package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.smartosc.fintech.risk.rulemanagement.common.constant.ErrorCode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponse<T> extends BaseResponse {
    @ApiModelProperty(notes = "data of response")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public SuccessResponse() {
        super(ErrorCode.SUCCESS.getMessage(), ErrorCode.SUCCESS.getCode());
    }

    public static <T> SuccessResponse<T> instance() {
        return new SuccessResponse<>();
    }

    public SuccessResponse<T> data(T data) {
        this.data = data;
        return this;
    }
}
