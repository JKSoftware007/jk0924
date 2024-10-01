package com.assessment.exceptions;

public class MissingRequiredFieldException extends Exception{
    public MissingRequiredFieldException() {
        super();
    }

    public MissingRequiredFieldException(String message) {
        super(message);
    }

    public MissingRequiredFieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public MissingRequiredFieldException(Throwable cause) {
        super(cause);
    }
}
