package com.smartosc.fintech.risk.rulemanagement.exception;

public class TimeInvalidException extends RuntimeException {
    private static final long serialVersionUID = 1769690779341414111L;

    public TimeInvalidException(String message) {
        super(message);
    }

    public TimeInvalidException() {

    }
}
