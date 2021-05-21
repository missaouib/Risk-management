package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.RuleType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RuleDto {

    private Long id;

    private String name;

    private Long effectiveDateStart;

    private Long effectiveDateEnd;

    private boolean status;

    private String description;

    private RuleType ruleType;


}
