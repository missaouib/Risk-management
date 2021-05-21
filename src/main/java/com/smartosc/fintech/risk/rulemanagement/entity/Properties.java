package com.smartosc.fintech.risk.rulemanagement.entity;

import com.smartosc.fintech.risk.rulemanagement.enumeration.DataTypeEnum;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Properties {
    private String name;
    private DataTypeEnum dataType;

}
