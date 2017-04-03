package com.example.demo.model.conditions;

/**
 * @author devendra.nalawade on 4/3/17
 */
public class ValidationError extends RuntimeException {
    public ValidationError() {
    }

    public ValidationError(String message) {
        super(message);
    }

    public ValidationError(String message, Throwable cause) {
        super(message, cause);
    }

    public ValidationError(Throwable cause) {
        super(cause);
    }

    public ValidationError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
