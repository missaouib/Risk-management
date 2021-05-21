package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.request.BusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DeleteBusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.ListBusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;

import java.util.List;

public interface BusinessRuleService {
    BusinessRuleResponse createBusinessRule(BusinessRuleRequest requestBody);
    RuleDetailResponse getRuleDetail(Long ruleId);
    BusinessRuleResponse updateBusinessRule(Long ruleId, BusinessRuleRequest requestBody);
    PagingResponse<RuleListResponse> getListRule(ListBusinessRuleRequest request);
    List<DeleteRuleResponse> deleteBusinessRule(DeleteBusinessRuleRequest request);
}
