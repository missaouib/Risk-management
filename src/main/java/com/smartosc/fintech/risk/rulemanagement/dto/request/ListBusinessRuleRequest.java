package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ListBusinessRuleRequest {
    Integer page = 1;
    Integer size = 10;
    String sort = "DESC";
    String sortBy = "id";
    Long ruleSetId;
}
