package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.entity.DataTypeEntity;
import com.smartosc.fintech.risk.rulemanagement.repository.DataTypeRepository;
import com.smartosc.fintech.risk.rulemanagement.service.DataTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class DataTypeServiceImpl implements DataTypeService {

    private final DataTypeRepository dataTypeRepository;

    @Override
    public Map<String, DataTypeEntity> getAllDataType() {
        List<DataTypeEntity> listType = dataTypeRepository.findAll();
        Map<String, DataTypeEntity> mapType = new HashMap<>();
        listType.forEach(type -> mapType.put(type.getCode(), type));
        return mapType;
    }
}
