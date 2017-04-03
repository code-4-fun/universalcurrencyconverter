package com.example.demo;

import com.example.demo.parser.InputProcessor;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class UniversalCurrencyConverter {

    public static void main(String[] args) {
        InputProcessor processor = new InputProcessor();
        processor.processData("data.txt");
        processor.resolveQueries();
    }

}
