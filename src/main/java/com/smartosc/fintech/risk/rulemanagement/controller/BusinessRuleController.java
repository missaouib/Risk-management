package com.smartosc.fintech.risk.rulemanagement.controller;

import com.smartosc.fintech.risk.rulemanagement.dto.request.BusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DeleteBusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.ListBusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RequestMapping("/${application-short-name}/${version}/business-rules")
@Api(value = "Business rule management")
@Validated
@CrossOrigin
public interface BusinessRuleController {

    @ApiOperation(value = "Create rule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BusinessRuleResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PostMapping
    SuccessResponse<BusinessRuleResponse> createBusinessRule(@Valid @RequestBody BusinessRuleRequest requestBody);

    @ApiOperation(value = "Get data rule by id", notes = "id is mandatory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = RuleDetailResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping("/{rule-id}")
    SuccessResponse<RuleDetailResponse> getBusinessRule(@PathVariable(name = "rule-id") @NotNull Long ruleId);

    @ApiOperation(value = "Update rule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = BusinessRuleResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PutMapping("/{rule-id}")
    SuccessResponse<BusinessRuleResponse> updateBusinessRule(@PathVariable(name = "rule-id") @NotNull Long ruleId, @Valid @RequestBody BusinessRuleRequest requestBody);

    @ApiOperation(value = "Get data list rule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = PagingResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @GetMapping
    SuccessResponse<PagingResponse<RuleListResponse>> getListBusinessRule(ListBusinessRuleRequest request);

    @ApiOperation(value = "Delete rule")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = DeleteRuleResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found Exception", response = ErrorResponse.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
    })
    @PutMapping
    SuccessResponse<List<DeleteRuleResponse>> deleteBusinessRule(@Valid @RequestBody DeleteBusinessRuleRequest request);

}
