package com.example.demo.model;

import com.example.demo.model.symbol.Symbol;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class Dictionary {

    private static final Map<Character, Symbol> dictionary = new HashMap<>();

    static {
        dictionary.put('I', new Symbol('I', 1, true, true));
        dictionary.put('V', new Symbol('V', 5, false, false));
        dictionary.put('X', new Symbol('X', 10, true, true));
        dictionary.put('L', new Symbol('L', 50, false, false));
        dictionary.put('C', new Symbol('C', 100, true, true));
        dictionary.put('D', new Symbol('D', 500, false, false));
        dictionary.put('M', new Symbol('M', 1000, true, true));
    }

    public static Map<Character, Symbol> getDictionary() {
        return dictionary;
    }

    public static Symbol parseSymbol(char input) {
        return dictionary.get(input);
    }

    public static List<Symbol> fetchValidElementsForSubtraction(char input) {
        Symbol symbol = dictionary.get(input);

        if (symbol == null) {
            return null;
        } else if (!symbol.isSubtractionAllowed()) {
            return null;
        } else {
            return dictionary.values()
                    .stream()
                    .sorted(Symbol::compareTo)
                    .filter(key -> key.getValue() > symbol.getValue())
                    .limit(2)
                    .collect(Collectors.toList());
        }
    }

    public static boolean hasSymbol(char ch) {
        return dictionary.containsKey(ch);
    }

    public static boolean hasSymbol(CharSequence ch) {
        return dictionary.containsKey(ch);
    }

    public static boolean hasDenomination(String denomination) {
        return getDenominations().containsKey(denomination);
    }

    public static Map<String, Integer> getDenominations() {
        Map<String, Integer> denominationMap = new HashMap<>();
        dictionary.forEach((key, symbol) -> {
            String denominationKey = String.valueOf(key);
            denominationMap.put(denominationKey, symbol.getValue());
            if (symbol.isSubtractionAllowed()) {
                fetchValidElementsForSubtraction(key)
                        .forEach(symb -> {
                            denominationMap.put(denominationKey + symb.getKey(), Math.subtractExact(symb.getValue(), symbol.getValue()));
                        });
            }
        });
        return denominationMap;
    }
}
