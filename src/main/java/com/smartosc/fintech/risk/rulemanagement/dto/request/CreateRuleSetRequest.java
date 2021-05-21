package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRuleSetRequest {
    private Long id;

    private String name;

    private Long effectiveStartDate;

    private Long effectiveEndDate;

    private boolean status;

    private Long dataModelGroupId;
}
