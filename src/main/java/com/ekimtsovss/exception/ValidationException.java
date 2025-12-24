package com.ekimtsovss.exception;


public class ValidationException extends Exception{

    public enum ErrorType {
        NO_ARGUMENTS,
        UNKNOWN_OPTION,
        MISSING_VALUE,
        INVALID_VALUE,
        MISSING_REQUIRED_ARG;
    }
    private final ErrorType errorType;

    public ValidationException(String message, ErrorType errorType) {
        super( message);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
