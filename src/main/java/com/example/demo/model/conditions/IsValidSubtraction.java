package com.example.demo.model.conditions;

import com.example.demo.model.Dictionary;
import com.example.demo.model.symbol.Symbol;

import java.util.List;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class IsValidSubtraction implements ICondition {

    private Symbol lastSymbol = null;

    /**
     * Check if the subtraction happens against the allowed combinations.
     * Also check if two or more values are being subtracted.
     *
     * @param input
     * @return
     */
    @Override
    public boolean isValid(char input) throws ValidationError {
        if (lastSymbol == null) {
            return true;
        }

        Symbol next = Dictionary.parseSymbol(input);
        List<Symbol> symbols = Dictionary.fetchValidElementsForSubtraction(lastSymbol.getKey());
        if (symbols == null) {
            throw new ValidationError("Subtraction on Input " + input + " not allowed");
        } else {
            if (!symbols.contains(next)) {
                throw new ValidationError("Input " + lastSymbol + " can not be subtracted from " + input);
            }
            return true;
        }
    }
}
