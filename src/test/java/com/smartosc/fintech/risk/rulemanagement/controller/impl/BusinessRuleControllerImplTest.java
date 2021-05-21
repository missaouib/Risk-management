package com.smartosc.fintech.risk.rulemanagement.controller.impl;

import com.smartosc.fintech.risk.rulemanagement.dto.request.BusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.BusinessRuleResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.RuleDetailResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import com.smartosc.fintech.risk.rulemanagement.service.BusinessRuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BusinessRuleControllerImplTest {

    @InjectMocks
    BusinessRuleControllerImpl businessRuleController;

    @Mock
    BusinessRuleService businessRuleService;

    @Test
    public void testCreateRuleSuccess() {
        BusinessRuleResponse returnResponse = new BusinessRuleResponse();
        returnResponse.setRuleId(100L);
        when(businessRuleService.createBusinessRule(any(BusinessRuleRequest.class))).thenReturn(returnResponse);
        SuccessResponse<BusinessRuleResponse> response = businessRuleController.createBusinessRule(new BusinessRuleRequest());
        assertThat(response.getCode()).isEqualTo("RSK-RE-200");
        assertThat(response.getMessage()).isEqualTo("OK");
        assertThat(response.getData().getRuleId()).isEqualTo(100L);
    }

    @Test
    public void testGetRuleDetail() {
        RuleDetailResponse returnResponse = new RuleDetailResponse();
        returnResponse.setRuleId(100L);
        when(businessRuleService.getRuleDetail(any(Long.class))).thenReturn(returnResponse);
        SuccessResponse<RuleDetailResponse> response = businessRuleController.getBusinessRule(100L);
        assertThat(response.getMessage()).isEqualTo("OK");
        assertThat(response.getCode()).isEqualTo("RSK-RE-200");
    }
}
