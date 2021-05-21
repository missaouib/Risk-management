package com.smartosc.fintech.risk.rulemanagement.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OperatorResponse {
    private Long id;

    private String label;

    private String value;

    private String description;
}
