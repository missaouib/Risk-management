package com.smartosc.fintech.risk.rulemanagement.controller.impl;

import com.smartosc.fintech.risk.rulemanagement.controller.BusinessRuleController;
import com.smartosc.fintech.risk.rulemanagement.dto.request.BusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DeleteBusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.ListBusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;
import com.smartosc.fintech.risk.rulemanagement.service.BusinessRuleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class BusinessRuleControllerImpl implements BusinessRuleController {

    private final BusinessRuleService businessRuleService;

    @Override
    public SuccessResponse<BusinessRuleResponse> createBusinessRule(BusinessRuleRequest requestBody) {
        return SuccessResponse.<BusinessRuleResponse>instance().data(businessRuleService.createBusinessRule(requestBody));
    }

    @Override
    public SuccessResponse<RuleDetailResponse> getBusinessRule(Long ruleId) {
        return SuccessResponse.<RuleDetailResponse>instance().data(businessRuleService.getRuleDetail(ruleId));
    }

    @Override
    public SuccessResponse<BusinessRuleResponse> updateBusinessRule(Long ruleId, BusinessRuleRequest requestBody) {
        return SuccessResponse.<BusinessRuleResponse>instance().data(businessRuleService.updateBusinessRule(ruleId, requestBody));
    }

    @Override
    public SuccessResponse<PagingResponse<RuleListResponse>> getListBusinessRule(ListBusinessRuleRequest request) {
        return SuccessResponse.<PagingResponse<RuleListResponse>>instance().data(businessRuleService.getListRule(request));
    }

    @Override
    public SuccessResponse<List<DeleteRuleResponse>> deleteBusinessRule(DeleteBusinessRuleRequest request) {
        return SuccessResponse.<List<DeleteRuleResponse>>instance().data(businessRuleService.deleteBusinessRule(request));
    }
}
