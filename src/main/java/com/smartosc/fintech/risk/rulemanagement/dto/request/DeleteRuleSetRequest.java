package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeleteRuleSetRequest {
    private List<Long> id;
    private boolean confirmDelete = false;
}
