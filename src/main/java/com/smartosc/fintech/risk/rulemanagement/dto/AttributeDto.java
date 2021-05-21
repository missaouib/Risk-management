package com.smartosc.fintech.risk.rulemanagement.dto;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DataTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author vuhq
 * @Since 2/17/2021
 */
@Getter
@Setter
public class AttributeDto {
    private Long id;
    private StatusEnum status;
    private String name;
    private DataTypeEnum dataType;
}
