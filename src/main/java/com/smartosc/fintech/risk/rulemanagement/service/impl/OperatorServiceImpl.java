package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.dto.response.OperatorResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.OperatorEntity;
import com.smartosc.fintech.risk.rulemanagement.service.OperatorService;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.OperatorMapper;
import com.smartosc.fintech.risk.rulemanagement.startup.DataMaster;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OperatorServiceImpl implements OperatorService {
    @Override
    public List<OperatorResponse> getAllOperator() {
        List<OperatorEntity> operatorEntities = new ArrayList<>();
        List<OperatorResponse> operatorResponses = new ArrayList<>();
        DataMaster.getOperatorCache().forEach((s, operatorEntity) -> operatorEntities.add(operatorEntity));
        Map<String, OperatorEntity> mapOperator = new HashMap<>();
        operatorEntities.forEach(operators -> mapOperator.put(operators.getLabel(), operators));
        operatorEntities.forEach(operatorEntity ->
                operatorResponses.add(OperatorMapper.INSTANCE.operatorEntityToOperatorResponse(operatorEntity)));
        return operatorResponses;
    }
}
