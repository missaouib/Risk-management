package com.smartosc.fintech.risk.rulemanagement.dto;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DataModelDto {
    private Long id;
    private GroupModelDto groupModel;
    private String modelName;
    private StatusEnum status;
    private Set<AttributeDto> properties;
}
