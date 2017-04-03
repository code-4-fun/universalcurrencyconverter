package com.example.demo.parser;

/**
 * @author devendra.nalawade on 4/3/17
 */
public interface IParser {

    void parse(String line);

    default boolean isLineEmpty(String line) {
        return (line == null || line.trim().length() <= 0);
    }

}
