package com.example.demo.parser;

import com.example.demo.model.mapper.CurrencyMapper;
import com.example.demo.model.symbol.Symbol;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class InputProcessor {

    private static final Logger LOGGER = Logger.getLogger(InputProcessor.class);
    private static final String DATA_DIR = "src/main/resources/data";

    private CombinedParser parser = null;

    public InputProcessor() {
        parser = new CombinedParser();
    }

    public void processData(String fileName) {

        if (fileName == null || fileName.trim().length() <= 0) {
            return;
        }

        File dataFile = new File(DATA_DIR + File.separator + fileName);
        LOGGER.info("Reading Data from File - " + dataFile.getAbsolutePath());

        if (dataFile.exists() && dataFile.isFile()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(dataFile));
                reader.lines().forEach(line -> parser.parse(line));

            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                        reader = null;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void resolveQueries() {
        ImprovisedSymbolsParser improvisedSymbolsParser = (ImprovisedSymbolsParser) parser.getParserOfType(ImprovisedSymbolsParser.class);
        Map<String, Symbol> symbolMap = improvisedSymbolsParser.getImprovisedSymbols();

        CreditRateParser creditRateParser = (CreditRateParser) parser.getParserOfType(CreditRateParser.class);
        Map<String, Integer> creditRateLedger = creditRateParser.getCreditRateLedger();

        Map<String, Integer> itemLedger = new HashMap<>();
        creditRateLedger.forEach((info, value) -> {
            String equation = info;
            Integer credit = value;

            Map.Entry<String, Integer> entry = calculateDelta(equation, symbolMap);
            itemLedger.put(entry.getKey(), (value / entry.getValue()));
        });

        symbolMap.forEach((s, value) -> LOGGER.debug("Local Currency - " + s + " : " + "Symbol - " + value));
        creditRateLedger.forEach((s, value) -> LOGGER.debug("Equation - " + s + " : " + "Value - " + value));
        itemLedger.forEach((s, value) -> LOGGER.debug("Item - " + s + " : " + "Value - " + value));

        QueryParser queryParser = (QueryParser) parser.getParserOfType(QueryParser.class);
        Map<QueryParser.QueryWrapper, String> queries = queryParser.getQueries();

        CurrencyMapper mapper = new CurrencyMapper();

        queries.forEach((query, equation) -> {
            LOGGER.info("Query Type - " + query.getEquation() + " : Equation - " + equation);

            if (QueryParser.QueryType.ValueQuery.equals(query.getQueryType())) {

                String[] words = equation.split(" ");
                String roman = "";
                for (String word : words) {
                    if (symbolMap.containsKey(word)) {
                        roman += symbolMap.get(word).getKey();
                    }
                }

                LOGGER.info("Answer: " + equation + " => " + mapper.romanToInt(roman));

            } else if (QueryParser.QueryType.CreditQuery.equals(query.getQueryType())) {

                String[] words = equation.split(" ");
                String item = words[words.length - 1];

                if (itemLedger.containsKey(item)) {
                    int requiredCreditsPerUnit = itemLedger.get(item);
                    String roman = "";
                    for (int i = 0; i < words.length - 1; i++) {
                        if (symbolMap.containsKey(words[i])) {
                            roman += symbolMap.get(words[i]).getKey();
                        }
                    }
                    int credits = mapper.romanToInt(roman) * requiredCreditsPerUnit;
                    LOGGER.info("Answer: " + equation + " => " + credits);
                }

            }
        });
    }


    private Map.Entry<String, Integer> calculateDelta(String equation, Map<String, Symbol> symbolMap) {
        String data[] = equation.split(" ");
        if (data.length == 1) {
            return new AbstractMap.SimpleEntry<String, Integer>(equation.trim(), 0);
        } else {
            String itemName = data[data.length - 1];
            int delta = 0;
            for (int i = 0; i < (data.length - 1); i++) {
                String denomination = data[i];
                if (symbolMap.containsKey(denomination)) {
                    delta += symbolMap.get(denomination).getValue();
                }
            }
            return new AbstractMap.SimpleEntry<String, Integer>(itemName.trim(), delta);
        }
    }


}
