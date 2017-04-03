package com.example.demo.model.mapper;

import com.example.demo.model.Dictionary;
import com.example.demo.model.conditions.CompositeCondition;
import com.example.demo.model.conditions.ICondition;
import com.example.demo.model.conditions.IsValidSubtraction;
import com.example.demo.model.conditions.RepeatableCondition;
import com.example.demo.model.conditions.ValidationError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class CurrencyMapper {

    private Map<String, Integer> denominations = null;

    public CurrencyMapper() {
        this.denominations = Dictionary.getDenominations();
    }

    public String numberToRoman(int number) {
        String roman = "";
        int runningValue = number;

        Integer[] magnitude = new Integer[denominations.size()];
        String[] symbols = new String[denominations.size()];

        AtomicInteger counter = new AtomicInteger(0);
        Dictionary.getDenominations()
                .entrySet()
                .stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .forEach(entry -> {
                    int index = counter.getAndIncrement();
                    symbols[index] = entry.getKey();
                    magnitude[index] = entry.getValue();
                });

        for (int x = 0; number > 0; x++) {
            runningValue = number / magnitude[x];
            for (int i = 1; i <= runningValue; i++) {
                roman = roman + symbols[x];
            }
            number = number % magnitude[x];
        }

        return roman;
    }

    public int romanToInt(String roman) {
        int len = roman.length();

        ICondition condition = getCondition();

        String lastChar = "";
        char curr;
        int sum = 0;
        for (int i = (roman.length() - 1); i >= 0; i--) {
            curr = roman.charAt(i);

            condition.isValid(curr);

            int value = 0;
            if (denominations.containsKey(curr + lastChar)) {
                value = denominations.get(curr + lastChar);

                if (denominations.containsKey(lastChar)) {
                    value -= denominations.get(lastChar);
                }

            } else if (denominations.containsKey(String.valueOf(curr))) {
                value = denominations.get(String.valueOf(curr));
            } else {
                throw new ValidationError("Denomination " + curr + " is not valid Roman Literal");
            }

            sum += value;
            lastChar = String.valueOf(curr);
        }
        return sum;
    }

    private ICondition getCondition() {
        List<ICondition> conditions = new ArrayList<>();
        conditions.add(new RepeatableCondition());
        conditions.add(new IsValidSubtraction());
        ICondition condition = new CompositeCondition(conditions);
        return condition;
    }

}
