package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.entity.OperatorEntity;
import com.smartosc.fintech.risk.rulemanagement.repository.OperatorRepository;
import com.smartosc.fintech.risk.rulemanagement.service.OperatorCacheService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class OperatorCacheServiceImpl implements OperatorCacheService {
    final OperatorRepository operatorRepository;

    @Override
    public Map<String, OperatorEntity> getAllOperators() {
        List<OperatorEntity> operatorEntities = operatorRepository.findAll();
        Map<String, OperatorEntity> mapOperator = new HashMap<>();
        operatorEntities.forEach(operators -> mapOperator.put(operators.getLabel(), operators));
        return mapOperator;
    }
}
