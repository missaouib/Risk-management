package com.smartosc.fintech.risk.rulemanagement.dto.request;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChildRuleListRequest {
    @NotNull
    private int order;
    private List<JsonNode> itemCondition = new ArrayList<>();
    @NotNull
    private RuleActionRequest action;
    @NotNull
    private List<DataModelCreateRuleRequest> dataModels = new ArrayList<>();
}
