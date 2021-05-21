package com.smartosc.fintech.risk.rulemanagement.entity;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ObjectModel {
    private String id;
    private String modelName;
    private String groupName;
    private StatusEnum status;
    private Set<Properties> properties;
}
