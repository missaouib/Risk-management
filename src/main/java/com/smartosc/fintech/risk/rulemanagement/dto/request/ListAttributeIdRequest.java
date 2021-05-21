package com.smartosc.fintech.risk.rulemanagement.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.smartosc.fintech.risk.rulemanagement.common.deserialize.LongDeserialize;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class ListAttributeIdRequest {

    @NotEmpty(message = "constraints.data.not.null")
    @UniqueElements
    @JsonDeserialize(contentUsing = LongDeserialize.class)
    @Valid
    private List<@NotNull @Min(value = 0) Long> ids;
}
