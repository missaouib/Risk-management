package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.entity.OperatorEntity;

import java.util.Map;

public interface OperatorCacheService {
    Map<String, OperatorEntity> getAllOperators();
}
