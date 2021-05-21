package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey;
import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import com.smartosc.fintech.risk.rulemanagement.dto.DataModelDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.AttributeRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DataModelRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.DataModelResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import com.smartosc.fintech.risk.rulemanagement.exception.DataExistedException;
import com.smartosc.fintech.risk.rulemanagement.exception.DataNotFoundException;
import com.smartosc.fintech.risk.rulemanagement.repository.AttributeRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.DataModelRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.specification.DataModelSpecification;
import com.smartosc.fintech.risk.rulemanagement.service.DataModelService;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.DataModelMapper;
import com.smartosc.fintech.risk.rulemanagement.startup.DataMaster;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author vuhq
 * @Since 2/2/2021
 */
@Service
@AllArgsConstructor
public class DataModelServiceImpl implements DataModelService {


    private final DataModelRepository dataModelRepository;
    private final AttributeRepository attributeRepository;

    @Override
    public void deleteDataModel(Long dataModelId) {
        DataModelEntity dataModelEntity = getDataModelByIdAndNotStatus(dataModelId, StatusEnum.DELETE);
        dataModelEntity.setStatus(StatusEnum.DELETE);
        dataModelRepository.save(dataModelEntity);
    }

    private DataModelEntity getDataModelByIdAndNotStatus(Long dataModelId, StatusEnum status) {
        return Optional
                .ofNullable(dataModelRepository.findByIdAndStatusNot(dataModelId, status))
                .orElseThrow(DataNotFoundException::new);
    }

    @Override
    public List<DataModelDto> getDataModelList(Long groupId) {
        GroupModelEntity groupModel = DataMaster.getModelGroupCache().get(groupId);
        List<DataModelEntity> dataModels = dataModelRepository.findAllByStatusNotAndGroupModel(StatusEnum.DELETE, groupModel);
        dataModels.forEach(modelEntity -> modelEntity.getProperties().removeIf(attributeEntity -> StatusEnum.DELETE.equals(attributeEntity.getStatus())));
        return DataModelMapper.INSTANCE.mapToDtoList(dataModels);
    }

    @Override
    public DataModelDto getDataModel(Long id) {
        DataModelEntity dataModel = getEntityWithAttributeAlive(id);
        return DataModelMapper.INSTANCE.mapToDto(dataModel);
    }

    @Override
    public DataModelResponse createDataModel(List<DataModelRequest> dataModelRequests) {
        DataModelResponse dataModelResponse = new DataModelResponse();
        List<DataModelEntity> dataModelEntities = new ArrayList<>();

        for (DataModelRequest dataModelRequest : dataModelRequests) {
            GroupModelEntity groupModel = DataMaster.getModelGroupCache().get(dataModelRequest.getGroupModelId());
            DataModelEntity dataModelEntity = checkExistedDataModel(dataModelRequest.getModelName(), groupModel);

            if (dataModelEntity != null && !dataModelEntity.getId().equals(dataModelRequest.getId())) {
                throw new DataExistedException(dataModelRequest.getModelName() + " " + ResourceUtil.getMessage(MessageKey.EXISTED));
            }

            dataModelEntity = DataModelMapper.INSTANCE.dataModelRequestToDataModelEntity(dataModelRequest);
            dataModelEntity.setStatus(StatusEnum.ACTIVE);
            dataModelEntity.setGroupModel(groupModel);

            Set<AttributeEntity> attributeEntities = new HashSet<>();

            for (AttributeRequest attributeRequest : dataModelRequest.getProperties()) {
                if (attributeRequest.getStatus() == null) {
                    attributeRequest.setStatus(StatusEnum.ACTIVE);
                }

                AttributeEntity attributeEntity = DataModelMapper.INSTANCE.attributeRequestToAttributeEntity(attributeRequest);
                if (attributeRequest.getId() != null) {
                    attributeEntity.setId(attributeRequest.getId());
                }

                attributeEntity.setType(attributeRequest.getDataType());
                attributeEntity.setStatus(attributeRequest.getStatus());
                attributeEntities.add(attributeEntity);
            }

            dataModelEntity.setProperties(attributeEntities);
            dataModelEntities.add(dataModelEntity);

        }
        List<DataModelEntity> listDataModelEntities = dataModelRepository.saveAll(dataModelEntities);

        List<Long> listId = new ArrayList<>();
        for (DataModelEntity dataModelEntity : listDataModelEntities) {
            listId.add(dataModelEntity.getId());
        }

        dataModelResponse.setIds(listId);
        dataModelResponse.setStatus(StatusEnum.SUCCESS);

        return dataModelResponse;
    }

    private DataModelEntity checkExistedDataModel(String modelName, GroupModelEntity groupModelEntity) {
        return dataModelRepository.findFirstByModelNameAndGroupModel(modelName, groupModelEntity);
    }


    @Override
    public DataModelEntity getEntityWithAttributeAlive(Long id) {
        Specification<DataModelEntity> spec = DataModelSpecification
                .spec()
                .withId(id)
                .notStatus(StatusEnum.DELETE)
                .build();
        DataModelEntity dataModelEntity = dataModelRepository.findOne(spec).orElseThrow(DataNotFoundException::new);
        dataModelEntity.getProperties().removeIf(entity -> StatusEnum.DELETE.equals(entity.getStatus()));
        return dataModelEntity;
    }
}
