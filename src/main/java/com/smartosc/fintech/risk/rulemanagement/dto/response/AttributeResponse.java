package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DataTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttributeResponse {
    private StatusEnum status;
    private String name;
    private DataTypeEnum dataType;
}
