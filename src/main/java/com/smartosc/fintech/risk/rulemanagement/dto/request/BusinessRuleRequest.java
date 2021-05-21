package com.smartosc.fintech.risk.rulemanagement.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.smartosc.fintech.risk.rulemanagement.enumeration.RuleType;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
public class BusinessRuleRequest {
    private Long ruleId;
    @NotNull
    @Min(value = 0)
    private Long dataModelGroup;
    @NotNull
    @Min(value = 0)
    private Long ruleSetId;
    private String ruleSetName;
    @NotBlank
    private String ruleName;
    private Timestamp effectiveDateStart;
    private Timestamp effectiveDateEnd;
    @NotNull
    private RuleType ruleType;
    @NotNull
    private boolean status;
    private String description;
    @NotNull
    private List<ChildRuleListRequest> listRule = new ArrayList<>();
    private JsonNode vocabularies;
}









