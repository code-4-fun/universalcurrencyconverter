package com.example.demo.parser;

import com.example.demo.model.conditions.ValidationError;

import java.util.HashMap;
import java.util.Map;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class CreditRateParser implements IParser {

    private Map<String, Integer> creditRateLedger = new HashMap<>();
    private Map<String, Integer> itemRateLedger = new HashMap<>();

    @Override
    public void parse(String line) {

        if (!isLineEmpty(line) && line.contains("is") && line.endsWith("Credits")) {
            line = line.substring(0, line.indexOf("Credits")).trim();

            String data[] = line.split(" is ");
            if (data.length != 2) {
                throw new ValidationError("Credit Rate Information Data is not correctly spelled - " + line);
            }

            creditRateLedger.put(data[0], Integer.parseInt(data[1].trim()));

        } else {
            return;
        }

    }

    public Map<String, Integer> getCreditRateLedger() {
        return creditRateLedger;
    }

}
