package com.example.demo.model.conditions;

/**
 * @author devendra.nalawade on 4/3/17
 */
public interface ICondition {

    boolean isValid(char input) throws ValidationError;

}
