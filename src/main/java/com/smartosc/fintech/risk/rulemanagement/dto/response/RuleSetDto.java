package com.smartosc.fintech.risk.rulemanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RuleSetDto {
    private Long id;

    private String name;

    private Long effectiveStartDate;

    private Long effectiveEndDate;

    private boolean status;

    private String modelGroup;

    private List<RulesResponse> listRule;

}
