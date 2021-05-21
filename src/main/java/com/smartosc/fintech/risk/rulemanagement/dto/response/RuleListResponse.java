package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import lombok.Data;

@Data
public class RuleListResponse {
    private Long groupModelId;
    private String groupModelName;
    private Long ruleSetId;
    private String ruleSetName;
    private Long ruleId;
    private String ruleName;
    private Long effectiveDateStart;
    private StateEnum status;
}
