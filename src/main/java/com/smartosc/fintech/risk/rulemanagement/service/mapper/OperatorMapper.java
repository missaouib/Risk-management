package com.smartosc.fintech.risk.rulemanagement.service.mapper;

import com.smartosc.fintech.risk.rulemanagement.dto.response.OperatorResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.OperatorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OperatorMapper {

    public static final OperatorMapper INSTANCE = Mappers.getMapper(OperatorMapper.class);

    public abstract OperatorResponse operatorEntityToOperatorResponse(OperatorEntity operatorEntity);
}
