package com.smartosc.fintech.risk.rulemanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupRuleSetResponse {
    private String name;
    List<RuleSetResponseList> listRuleSet;
}
