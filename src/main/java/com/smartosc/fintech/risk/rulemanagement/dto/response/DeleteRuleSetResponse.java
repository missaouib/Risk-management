package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteRuleSetResponse {
    private Long id;
    private String name;
    private List<RulesResponse> listRule;
    private StatusEnum status = StatusEnum.SUCCESS;

}
