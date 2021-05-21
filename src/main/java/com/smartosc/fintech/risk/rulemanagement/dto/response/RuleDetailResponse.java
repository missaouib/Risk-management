package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class RuleDetailResponse {
    private Long ruleId;
    private JsonNode ruleDetail;
}
