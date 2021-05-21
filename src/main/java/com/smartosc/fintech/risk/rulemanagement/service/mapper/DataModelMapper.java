package com.smartosc.fintech.risk.rulemanagement.service.mapper;

import com.smartosc.fintech.risk.rulemanagement.dto.AttributeDto;
import com.smartosc.fintech.risk.rulemanagement.dto.DataModelDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.AttributeRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DataModelRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.AttributeResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.DataModelEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DataModelMapper {
    public static final DataModelMapper INSTANCE = Mappers.getMapper(DataModelMapper.class);

    @Named("mapDataModelEntityToDto")
    public abstract DataModelDto mapToDto(DataModelEntity modelEntity);

    /* must return the exact LinkedHashSet to sort the data retrieved from db */
    public abstract Set<AttributeDto> mapToEntities(Set<AttributeEntity> entitySet);

    public abstract List<DataModelDto> mapToDtoList(List<DataModelEntity> entities);

    public abstract AttributeEntity attributeRequestToAttributeEntity(AttributeRequest attributeRequest);

    @Mapping(target = "dataType", source = "type")
    public abstract AttributeDto attributeEntityToAttributeEntity(AttributeEntity attributeEntity);

    public abstract DataModelEntity dataModelRequestToDataModelEntity(DataModelRequest dataModelRequest);

    @Mapping(target = "dataType", source = "type")
    public abstract AttributeResponse attributeEntityToResponse(AttributeEntity attribute);

    public abstract AttributeResponse attributeRequestToResponse(AttributeRequest attribute);

}

