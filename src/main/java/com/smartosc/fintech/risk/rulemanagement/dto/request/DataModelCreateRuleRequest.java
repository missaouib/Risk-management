package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class DataModelCreateRuleRequest {
    @NotNull
    private String tableName;

    @NotNull
    private List<AttributeCreateRuleRequest> attributes = new ArrayList<>();
}
