package com.example.demo.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class CombinedParser implements IParser {

    private List<IParser> parserList = new ArrayList<>();

    public CombinedParser() {
        parserList.add(new CreditRateParser());
        parserList.add(new ImprovisedSymbolsParser());
        parserList.add(new QueryParser());
    }

    @Override
    public void parse(String line) {
        parserList.forEach(iParser -> iParser.parse(line));
    }

    public List<IParser> getParserList() {
        return parserList;
    }

    public IParser getParserOfType(Class<? extends IParser> type) {
        return parserList.stream()
                .filter(iParser -> type.isInstance(iParser))
                .collect(Collectors.toList())
                .get(0);
    }
}
