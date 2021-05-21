package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class DeleteBusinessRuleRequest {
    private List<Long> ids;
}
