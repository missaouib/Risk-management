package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AttributeCreateRuleRequest {
    @NotNull
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String dataType;
}
