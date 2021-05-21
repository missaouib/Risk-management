package com.smartosc.fintech.risk.rulemanagement.dto.request;

import com.smartosc.fintech.risk.rulemanagement.common.validator.GroupModel;
import com.smartosc.fintech.risk.rulemanagement.common.validator.NameAndIdOfAttributes;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author vuhq
 * @Since 2/4/2021
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DataModelRequest {
    private Long id;
    @NotBlank(message = "constraints.data.not.null")
    private String modelName;
    @NotNull
    @GroupModel
    private Long groupModelId;
    @NotNull
    @Valid
    @NameAndIdOfAttributes
    private Set<AttributeRequest> properties;
}
