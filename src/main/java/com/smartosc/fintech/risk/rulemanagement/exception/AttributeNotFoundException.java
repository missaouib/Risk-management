package com.smartosc.fintech.risk.rulemanagement.exception;

import lombok.Getter;

@Getter
public class AttributeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 7790031132989747074L;
    private final String attributeIds;

    public AttributeNotFoundException(String attributeId) {
        this.attributeIds = attributeId;
    }
}
