package com.smartosc.fintech.risk.rulemanagement.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DataNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1690240631267697094L;

    public DataNotFoundException() {
        super();
    }

}
