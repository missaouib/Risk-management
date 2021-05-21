package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RuleActionRequest {
    @NotNull
    private String returnType;
    @NotNull
    private String value;
}
