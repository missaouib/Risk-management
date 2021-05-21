package com.smartosc.fintech.risk.rulemanagement.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.smartosc.fintech.risk.rulemanagement.common.deserialize.StatusEnumDeserialize;
import com.smartosc.fintech.risk.rulemanagement.common.validator.DataType;
import com.smartosc.fintech.risk.rulemanagement.common.validator.group.FirstGroup;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author vuhq
 * @Since 3/3/2021
 */
@Getter
@Setter
public class UpdateAttributeRequest {
    @NotNull
    @JsonDeserialize(using = StatusEnumDeserialize.class)
    private StatusEnum status;
    @NotBlank(message = "constraints.data.not.null")
    private String name;
    @DataType(groups = FirstGroup.class)
    @NotBlank(message = "constraints.data.not.null")
    private String dataType;
}
