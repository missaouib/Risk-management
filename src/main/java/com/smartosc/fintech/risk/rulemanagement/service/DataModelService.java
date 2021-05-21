package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.DataModelDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DataModelRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.DataModelResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity;

import java.util.List;

/**
 * @Author vuhq
 * @Since 2/2/2021
 */
public interface DataModelService {
    void deleteDataModel(Long dataModelId);

    List<DataModelDto> getDataModelList(Long groupId);

    DataModelDto getDataModel(Long id);

    DataModelResponse createDataModel(List<DataModelRequest> dataModelRequests);

    DataModelEntity getEntityWithAttributeAlive(Long id);
}
