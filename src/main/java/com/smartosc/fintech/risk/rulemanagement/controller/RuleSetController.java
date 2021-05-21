package com.smartosc.fintech.risk.rulemanagement.controller;

import com.smartosc.fintech.risk.rulemanagement.dto.request.CreateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DeleteRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RequestMapping("/${application-short-name}/${version}/rule-set")
@Api(value = "rule set management")
@Validated
public interface RuleSetController {


    @ApiOperation(value = "create rule set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @PostMapping
    SuccessResponse<RuleSetDto> createRuleSet(@RequestBody CreateRuleSetRequest createRuleSetRequest);


    @ApiOperation(value = "get list rule set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @GetMapping
    SuccessResponse<ListRuleSetResponse> getListRuleSet(@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
                                                        @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                                        @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort,
                                                        @RequestParam(name = "sortBY", required = false, defaultValue = "id") String sortBy);


    @ApiOperation(value = "delete rule set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    @PutMapping("/delete")
    SuccessResponse<List<DeleteRuleSetResponse>> deleteRuleSet(@RequestBody DeleteRuleSetRequest deleteRuleSetRequest);


    @ApiOperation(value = "get rule set by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    @GetMapping("{id}")
    SuccessResponse<RuleSetResponse> getRuleSetById(@PathVariable Long id);

    @ApiOperation(value = "get rule set by group")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
    })
    @GetMapping(value = "get-by-group/{groupId}")
    SuccessResponse<List<RuleSetByGroupResponse>> getRuleSetByGroup(@PathVariable Long groupId);


    @ApiOperation(value = "update rule set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @PutMapping("/update")
    SuccessResponse<UpdateRuleSetResponse> updateRuleSet(@RequestBody UpdateRuleSetRequest updateRuleSetRequest);

    @ApiOperation(value = "group rule set by ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = SuccessResponse.class),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class)
    })
    @GetMapping("/group")
    SuccessResponse<List<GroupRuleSetResponse>> groupRuleSet();


}
