package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.dto.AttributeDto;
import com.smartosc.fintech.risk.rulemanagement.dto.response.AttributeResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import com.smartosc.fintech.risk.rulemanagement.exception.AttributeNotFoundException;
import com.smartosc.fintech.risk.rulemanagement.exception.DataNotFoundException;
import com.smartosc.fintech.risk.rulemanagement.exception.NameAttributeDuplicateException;
import com.smartosc.fintech.risk.rulemanagement.repository.AttributeRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.specification.AttributeSpecification;
import com.smartosc.fintech.risk.rulemanagement.service.AttributeService;
import com.smartosc.fintech.risk.rulemanagement.service.DataModelService;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.AttributeMapper;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.DataModelMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class AttributeServiceImpl implements AttributeService {

    private final AttributeRepository attributeRepository;
    private final DataModelService dataModelService;

    @Override
    public void deleteAttribute(Long dataModelId, Long attributeId) {
        AttributeEntity attribute = getAttributeAlive(attributeId, dataModelId, StatusEnum.DELETE);
        attribute.setStatus(StatusEnum.DELETE);
        attributeRepository.save(attribute);
    }

    @Override
    public void deleteMultiAttribute(Long dataModelId, List<Long> ids) {
        DataModelEntity dataModelEntity = getDataModelWithAttributeAlive(dataModelId);
        List<AttributeEntity> attributeList = validateListAttributeId(dataModelEntity.getProperties(), ids);
        attributeList.forEach(entity -> entity.setStatus(StatusEnum.DELETE));
        attributeRepository.saveAll(attributeList);
    }

    private DataModelEntity getDataModelWithAttributeAlive(Long id) {
        return dataModelService.getEntityWithAttributeAlive(id);
    }

    private List<AttributeEntity> validateListAttributeId(Set<AttributeEntity> attributeSet, List<Long> ids) {
        if (CollectionUtils.isEmpty(attributeSet)) {
            throw new AttributeNotFoundException(ids.toString());
        }

        Map<Long, AttributeEntity> attributeMap = new HashMap<>();
        for (AttributeEntity attribute : attributeSet) {
            attributeMap.put(attribute.getId(), attribute);
        }

        List<AttributeEntity> attributeNewList = new ArrayList<>();
        List<Long> attributeIdNotExist = new ArrayList<>();
        AttributeEntity attributeNew;
        for (Long id : ids) {
            if (id != null && !attributeMap.containsKey(id)) {
                attributeIdNotExist.add(id);
            } else {
                attributeNew = attributeMap.get(id);
                attributeNew.setStatus(StatusEnum.DELETE);
                attributeNewList.add(attributeNew);
            }
        }

        if (!CollectionUtils.isEmpty(attributeIdNotExist)) {
            throw new AttributeNotFoundException(attributeIdNotExist.toString());
        }

        return attributeNewList;

    }

    @Override
    public AttributeResponse updateAttribute(Long dataModelId, Long attributeId, AttributeDto attributeDto) {
        DataModelEntity dataModelEntity = getDataModelWithAttributeAlive(dataModelId);
        AttributeEntity attribute = getAttribute(dataModelEntity.getProperties(), attributeId, attributeDto);
        AttributeMapper.INSTANCE.attributeDtoToEntity(attributeDto, attribute);
        attribute = attributeRepository.save(attribute);
        return DataModelMapper.INSTANCE.attributeEntityToResponse(attribute);
    }

    private AttributeEntity getAttribute(Set<AttributeEntity> attributeSet, Long attributeId, AttributeDto attributeDto) {
        validateAttributeSet(attributeSet);
        validateNameAttribute(attributeSet, attributeDto.getName(), attributeId, attributeDto.getStatus());
        return getAttributeInSet(attributeSet, attributeId);

    }

    private AttributeEntity getAttributeInSet(Set<AttributeEntity> attributeSet, Long attributeId) {
        return attributeSet
                .stream()
                .filter(entity -> attributeId.equals(entity.getId()))
                .findFirst()
                .orElseThrow(DataNotFoundException::new);
    }

    private void validateAttributeSet(Set<AttributeEntity> attributeSet) {
        if (CollectionUtils.isEmpty(attributeSet)) {
            throw new DataNotFoundException();
        }
    }

    private void validateNameAttribute(Set<AttributeEntity> attributeSet, String name, Long attributeId, StatusEnum status) {
        if (StatusEnum.ACTIVE.equals(status)) {
            Set<AttributeEntity> invalidSet = attributeSet
                    .stream()
                    .filter(entity -> name.equals(entity.getName())
                            && attributeId.equals(entity.getId())
                            && status.equals(entity.getStatus()))
                    .collect(Collectors.toSet());
            if (CollectionUtils.isEmpty(invalidSet)) {
                throw new NameAttributeDuplicateException();
            }
        }
    }

    private AttributeEntity getAttributeAlive(Long attributeId, Long dataModelId, StatusEnum status) {
        Specification<AttributeEntity> spec =
                AttributeSpecification
                .spec()
                .withId(attributeId)
                .withNotStatus(status)
                .withModelId(dataModelId)
                .withModelNotStatus(status)
                .build();
        return attributeRepository
                .findOne(spec)
                .orElseThrow(DataNotFoundException::new);
    }

}
