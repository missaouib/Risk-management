package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RuleEngineRequest {
    private List<BusinessRuleRequest> rules = new ArrayList<>();
}
