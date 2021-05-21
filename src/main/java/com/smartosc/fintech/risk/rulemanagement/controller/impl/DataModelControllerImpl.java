package com.smartosc.fintech.risk.rulemanagement.controller.impl;

import com.smartosc.fintech.risk.rulemanagement.config.DBContextHolder;
import com.smartosc.fintech.risk.rulemanagement.controller.DataModelController;
import com.smartosc.fintech.risk.rulemanagement.dto.AttributeDto;
import com.smartosc.fintech.risk.rulemanagement.dto.DataModelDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DataModelRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.ListAttributeIdRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateAttributeRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.AttributeResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.DataModelResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.service.AttributeService;
import com.smartosc.fintech.risk.rulemanagement.service.DataModelService;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.AttributeMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author vuhq
 * @Since 2/2/2021
 */
@RestController
@Slf4j
@AllArgsConstructor
public class DataModelControllerImpl implements DataModelController {

    private final DataModelService dataModelService;

    private final AttributeService attributeService;

    @Override
    public SuccessResponse<String> deleteDataModel(Long dataModelId) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        dataModelService.deleteDataModel(dataModelId);
        return new SuccessResponse<>();
    }

    @Override
    public SuccessResponse<String> deleteAttribute(Long dataModelId, Long attributeId) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        attributeService.deleteAttribute(dataModelId, attributeId);
        return new SuccessResponse<>();
    }

    @Override
    public SuccessResponse<String> deleteMultiAttribute(Long dataModelId, ListAttributeIdRequest ids) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        attributeService.deleteMultiAttribute(dataModelId, ids.getIds());
        return new SuccessResponse<>();
    }

    @Override
    public SuccessResponse<AttributeResponse> updateAttribute(Long dataModelId, Long attributeId, UpdateAttributeRequest request) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        AttributeDto attributeDto = AttributeMapper.INSTANCE.attributeRequestToDTO(request);
        return SuccessResponse
                .<AttributeResponse>instance()
                .data(attributeService
                        .updateAttribute(dataModelId, attributeId, attributeDto));
    }

    @Override
    public SuccessResponse<List<DataModelDto>> getDataModelByGroup(Long groupId) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        List<DataModelDto> modelDtos = dataModelService.getDataModelList(groupId);
        return SuccessResponse.<List<DataModelDto>>instance().data(modelDtos);
    }

    @Override
    public SuccessResponse<DataModelDto> getDataModel(Long id) {
        DBContextHolder.setCurrentDb(DBTypeEnum.MAIN);
        DataModelDto modelDto = dataModelService.getDataModel(id);
        return SuccessResponse.<DataModelDto>instance().data(modelDto);
    }

    @Override
    public SuccessResponse<DataModelResponse> createDataModel(List<DataModelRequest> dataModelRequests) {
        return SuccessResponse.<DataModelResponse>instance().data(dataModelService.createDataModel(dataModelRequests));
    }
}
