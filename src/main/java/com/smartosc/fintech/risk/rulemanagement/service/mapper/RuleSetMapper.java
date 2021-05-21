package com.smartosc.fintech.risk.rulemanagement.service.mapper;

import com.smartosc.fintech.risk.rulemanagement.dto.request.CreateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.RuleDto;
import com.smartosc.fintech.risk.rulemanagement.dto.response.RuleSetDto;
import com.smartosc.fintech.risk.rulemanagement.dto.response.RuleSetResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.RulesResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleSetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class RuleSetMapper {

    public static final RuleSetMapper INSTANCE = Mappers.getMapper(RuleSetMapper.class);

    public abstract RuleSetEntity ruleSetRequestMapToRuleSetEntity(CreateRuleSetRequest createRuleSetRequest);

    public abstract RuleSetEntity ruleSetRequestUpdateMapToRuleSetEntity(UpdateRuleSetRequest updateRuleSetRequest);

    public abstract RuleSetResponse ruleSetEntityMapToRuleSetResponse(RuleSetEntity ruleSetEntity);

    public abstract RuleSetDto ruleSetEntitytMapToRuleSetDto(RuleSetEntity ruleSetEntity);

    public abstract RulesResponse ruleEntityToRuleResponse(RuleEntity ruleEntity);

    @Mapping(target = "effectiveDateStart", ignore = true)
    @Mapping(target = "effectiveDateEnd", ignore = true)
    public abstract RuleDto ruleEntitytMapToRuleDto(RuleEntity ruleEntity);

    public abstract RulesResponse ruleEntitytMapToRuleResponse(RuleEntity ruleEntity);

    @Named("entityToDto")
    public static RuleSetDto entityToDto(RuleSetEntity ruleSetEntity, GroupModelEntity groupModel) {
        RuleSetDto ruleSetDto = RuleSetMapper.INSTANCE.ruleSetEntitytMapToRuleSetDto(ruleSetEntity);
        ruleSetDto.setModelGroup(groupModel.getName());
        ruleSetDto.setEffectiveStartDate(DateTimeMapper.timestampToLong(ruleSetEntity.getEffectiveDateStart()));
        ruleSetDto.setEffectiveEndDate(DateTimeMapper.timestampToLong(ruleSetEntity.getEffectiveDateEnd()));
        return ruleSetDto;
    }

    @Named("mapRequestToEntity")
    public static RuleSetEntity mapRequestToEntity(GroupModelEntity groupModel, CreateRuleSetRequest createRuleSetRequest) {
        RuleSetEntity ruleSetEntity;
        ruleSetEntity = RuleSetMapper.INSTANCE.ruleSetRequestMapToRuleSetEntity(createRuleSetRequest);
        ruleSetEntity.setDataModelGroup(groupModel);
        ruleSetEntity.setEffectiveDateStart(DateTimeMapper.longToTimestamp(createRuleSetRequest.getEffectiveStartDate()));
        ruleSetEntity.setEffectiveDateEnd(DateTimeMapper.longToTimestamp(createRuleSetRequest.getEffectiveEndDate()));
        return ruleSetEntity;
    }

    @Named("mapRequestToEntity")
    public static RuleSetEntity mapRequestUpdateToEntity(GroupModelEntity groupModel, UpdateRuleSetRequest updateRuleSetRequest) {
        RuleSetEntity ruleSetEntity;
        ruleSetEntity = RuleSetMapper.INSTANCE.ruleSetRequestUpdateMapToRuleSetEntity(updateRuleSetRequest);
        ruleSetEntity.setDataModelGroup(groupModel);
        ruleSetEntity.setEffectiveDateStart(DateTimeMapper.longToTimestamp(updateRuleSetRequest.getEffectiveStartDate()));
        ruleSetEntity.setEffectiveDateEnd(DateTimeMapper.longToTimestamp(updateRuleSetRequest.getEffectiveEndDate()));
        return ruleSetEntity;
    }

    @Named("mapEntityToResponse")
    public static List<RuleSetResponse> mapEntityToResponse(Page<RuleSetEntity> ruleSetEntities, String format) {

        List<RuleSetResponse> ruleSetDtos = new ArrayList<>();
        for (RuleSetEntity ruleSetEntity : ruleSetEntities) {

            RuleSetResponse ruleSetDto = INSTANCE.mapRuleSetEntityToRuleSetResponse(ruleSetEntity, format);
            ruleSetDtos.add(ruleSetDto);

        }
        return ruleSetDtos;
    }

    public String mapperId(Long id, String format) {
        return String.format(format, id);
    }

    public RuleSetResponse mapRuleSetEntityToRuleSetResponse(RuleSetEntity ruleSetEntity, String format) {

        List<RuleDto> ruleDtos = new ArrayList<>();

        for (RuleEntity ruleEntity : ruleSetEntity.getRules()) {
            RuleDto ruleDTO = RuleSetMapper.INSTANCE.ruleEntitytMapToRuleDto(ruleEntity);
            ruleDTO.setEffectiveDateStart(DateTimeMapper.timestampToLong(ruleEntity.getEffectiveDateStart()));
            ruleDTO.setEffectiveDateEnd(DateTimeMapper.timestampToLong(ruleEntity.getEffectiveDateEnd()));
            ruleDtos.add(ruleDTO);
        }
        RuleSetResponse ruleSetDto = RuleSetMapper.INSTANCE.ruleSetEntityMapToRuleSetResponse(ruleSetEntity);
        ruleSetDto.setEffectiveStartDate(DateTimeMapper.timestampToLong(ruleSetEntity.getEffectiveDateStart()));
        ruleSetDto.setEffectiveEndDate(DateTimeMapper.timestampToLong(ruleSetEntity.getEffectiveDateEnd()));
        ruleSetDto.setDisplayId(mapperId(ruleSetEntity.getId(), format));
        ruleSetDto.setId(ruleSetEntity.getId());
        ruleSetDto.setListRule(ruleDtos);

        return ruleSetDto;
    }

}
