package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse<T> extends BaseResponse {

    @ApiModelProperty(notes = "detail of error")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T details;

    public ErrorResponse(String code, String message) {
        super(code, message);
    }

    public ErrorResponse(BaseResponse baseResponse) {
        super(baseResponse);
    }


    public ErrorResponse<T> fail(T details) {
        this.details = details;
        return this;
    }
}
