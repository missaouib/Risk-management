package com.smartosc.fintech.risk.rulemanagement.dto.request;

import com.smartosc.fintech.risk.rulemanagement.common.validator.Attribute;
import com.smartosc.fintech.risk.rulemanagement.enumeration.DataTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * @Author vuhq
 * @Since 2/5/2021
 */
@Getter
@Setter
@Attribute(name = "name", type = "dataType")
public class AttributeRequest {
    private Long id;
    private StatusEnum status;
    @NotBlank
    private String name;

    @NotNull
    private DataTypeEnum dataType;
}
