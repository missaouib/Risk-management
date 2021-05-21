package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeleteRuleResponse {
    private Long id;
    private String name;
    private StatusEnum status = StatusEnum.SUCCESS;
}
