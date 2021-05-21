package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.response.OperatorResponse;

import java.util.List;

public interface OperatorService {
    List<OperatorResponse> getAllOperator();
}
