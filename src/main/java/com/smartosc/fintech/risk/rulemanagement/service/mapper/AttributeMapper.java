package com.smartosc.fintech.risk.rulemanagement.service.mapper;

import com.smartosc.fintech.risk.rulemanagement.dto.AttributeDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateAttributeRequest;
import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.DataTypeEntity;
import com.smartosc.fintech.risk.rulemanagement.startup.DataMaster;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class AttributeMapper {
    public static final AttributeMapper INSTANCE = Mappers.getMapper(AttributeMapper.class);

    @Mapping(target = "dataModel",ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "type", source = "dataType")
    public abstract void attributeDtoToEntity(AttributeDto dto, @MappingTarget AttributeEntity entity);

    public DataTypeEntity attributeDtoToEntity(String dataType) {
        if ( dataType == null ) {
            return null;
        }
        return DataMaster.getDataTypeCache().get(dataType);
    }

    @Mapping(target = "name", expression = "java(request.getName().trim())")
    public abstract AttributeDto attributeRequestToDTO(UpdateAttributeRequest request);

}
