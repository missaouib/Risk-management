package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.entity.DataTypeEntity;

import java.util.Map;

public interface DataTypeService {
    Map<String, DataTypeEntity> getAllDataType();
}
