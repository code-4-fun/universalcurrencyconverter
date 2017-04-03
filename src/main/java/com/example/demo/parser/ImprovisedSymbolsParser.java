package com.example.demo.parser;

import com.example.demo.model.Dictionary;
import com.example.demo.model.conditions.ValidationError;
import com.example.demo.model.symbol.Symbol;

import java.util.HashMap;
import java.util.Map;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class ImprovisedSymbolsParser implements IParser {

    private static final Map<String, Symbol> improvisedSymbols = new HashMap<>();

    @Override
    public void parse(String line) {

        if (isLineEmpty(line)) {
            return;
        }

        // split by word
        String data[] = line.split(" ");
        if (data.length == 3 && "is".equals(data[1])) {
            char ch = data[2].charAt(0);
            if (Dictionary.hasSymbol(ch)) {
                Symbol symbol = Dictionary.parseSymbol(ch);
                improvisedSymbols.put(data[0], symbol);
            } else {
                throw new ValidationError("Invalid Symbol " + data[2]);
            }
        } else {
            return;
        }

    }

    public static Map<String, Symbol> getImprovisedSymbols() {
        return improvisedSymbols;
    }
}
