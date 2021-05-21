package com.smartosc.fintech.risk.rulemanagement.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class RuleEngineResponse {
    private String code = "";
    private String message;
    private List<String> details;
}
