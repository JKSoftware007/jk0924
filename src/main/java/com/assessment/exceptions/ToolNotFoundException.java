package com.assessment.exceptions;

public class ToolNotFoundException extends Exception{
    public ToolNotFoundException() {
        super();
    }

    public ToolNotFoundException(String message) {
        super(message);
    }

    public ToolNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ToolNotFoundException(Throwable cause) {
        super(cause);
    }
}
