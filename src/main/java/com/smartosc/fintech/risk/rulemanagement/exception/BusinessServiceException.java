package com.smartosc.fintech.risk.rulemanagement.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class BusinessServiceException extends RuntimeException {
    private static final long serialVersionUID = -8784028701313455411L;
    private final int code;

    public BusinessServiceException(String message, int code) {
        super(message);
        this.code = code;
    }
}
