package com.example.demo.model.conditions;

import java.util.List;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class CompositeCondition implements ICondition {

    private List<ICondition> conditions = null;

    public CompositeCondition(List<ICondition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public boolean isValid(char inputChar) throws ValidationError {
        return conditions
                .stream()
                .map(iCondition -> iCondition.isValid(inputChar))
                .reduce((result1, result2) -> (result1 && result2))
                .get();
    }
}
