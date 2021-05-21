package com.smartosc.fintech.risk.rulemanagement.controller;


import com.smartosc.fintech.risk.rulemanagement.dto.response.ErrorResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.OperatorResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin
@RequestMapping("/${application-short-name}/${version}/operator")
@Api(value = "operator management")
@Validated
public interface OperatorController {

    @ApiOperation(value = "get operator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @GetMapping
    SuccessResponse<List<OperatorResponse>> getAllOperators();
}
