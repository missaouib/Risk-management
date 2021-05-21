package com.smartosc.fintech.risk.rulemanagement.controller.impl;

import com.smartosc.fintech.risk.rulemanagement.controller.RuleSetController;
import com.smartosc.fintech.risk.rulemanagement.dto.request.CreateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DeleteRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;
import com.smartosc.fintech.risk.rulemanagement.service.RuleSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RuleSetControllerImpl implements RuleSetController {

    final RuleSetService ruleSetService;

    @Autowired
    public RuleSetControllerImpl(RuleSetService ruleSetService) {
        this.ruleSetService = ruleSetService;
    }

    @Override
    public SuccessResponse<RuleSetDto> createRuleSet(CreateRuleSetRequest createRuleSetRequest) {
        return SuccessResponse
                .<RuleSetDto>instance()
                .data(ruleSetService.createRuleSet(createRuleSetRequest));
    }

    @Override
    public SuccessResponse<ListRuleSetResponse> getListRuleSet(Integer page, Integer size, String sort, String sortBy) {
        return SuccessResponse
                .<ListRuleSetResponse>instance()
                .data(ruleSetService.getListRuleSet(page, size, sort, sortBy));
    }

    @Override
    public SuccessResponse<List<DeleteRuleSetResponse>> deleteRuleSet(DeleteRuleSetRequest deleteRuleSetRequest) {
        return SuccessResponse
                .<List<DeleteRuleSetResponse>>instance()
                .data(ruleSetService.deleteRuleSet(deleteRuleSetRequest));
    }

    @Override
    public SuccessResponse<RuleSetResponse> getRuleSetById(Long id) {
        return SuccessResponse
                .<RuleSetResponse>instance()
                .data(ruleSetService.getRuleSetById(id));
    }

    @Override
    public SuccessResponse<List<RuleSetByGroupResponse>> getRuleSetByGroup(Long groupId) {

        return SuccessResponse
                .<List<RuleSetByGroupResponse>>instance()
                .data(ruleSetService.getRuleSetByGroup(groupId));
    }

    @Override
    public SuccessResponse<UpdateRuleSetResponse> updateRuleSet(UpdateRuleSetRequest updateRuleSetRequest) {
        return SuccessResponse
                .<UpdateRuleSetResponse>instance()
                .data(ruleSetService.updateRuleSet(updateRuleSetRequest));
    }

    @Override
    public SuccessResponse<List<GroupRuleSetResponse>> groupRuleSet() {
        return SuccessResponse
                .<List<GroupRuleSetResponse>>instance()
                .data(ruleSetService.groupRuleSet());
    }
}