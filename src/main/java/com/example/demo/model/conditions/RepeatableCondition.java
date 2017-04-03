package com.example.demo.model.conditions;

import com.example.demo.model.Dictionary;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class RepeatableCondition implements ICondition {

    private char lastChar = 0;
    private int repeatedLiteralCount = 0;

    /**
     * Should check if the repeated characters belong to Repeatable repeatableSymbols.
     * Also, check if number of consecutive repeats <= 3
     *
     * @param inputChar
     * @return
     */
    @Override
    public boolean isValid(char inputChar) throws ValidationError {
        if (Dictionary.hasSymbol(inputChar)) {
            if (inputChar == lastChar) {
                repeatedLiteralCount++;
            } else {
                repeatedLiteralCount = 0;
            }

            lastChar = inputChar;

            if (repeatedLiteralCount >= 3) {
                throw new ValidationError("Symbol " + inputChar + " appeared 3 times in succession");
            }

        } else {
            throw new ValidationError("Symbol - " + inputChar + " is not valid");
        }

        return true;
    }

}
