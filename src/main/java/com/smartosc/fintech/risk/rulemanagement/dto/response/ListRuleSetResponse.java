package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.dto.PageableData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ListRuleSetResponse {
    List<RuleSetResponse> ruleSetResponses;

    PageableData pageableData;
}
