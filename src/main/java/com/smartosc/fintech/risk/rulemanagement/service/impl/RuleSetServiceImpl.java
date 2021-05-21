package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey;
import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import com.smartosc.fintech.risk.rulemanagement.config.UrlStorageConfig;
import com.smartosc.fintech.risk.rulemanagement.dto.PageableData;
import com.smartosc.fintech.risk.rulemanagement.dto.request.*;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleSetEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.ErrorCodeEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import com.smartosc.fintech.risk.rulemanagement.exception.DataExistedException;
import com.smartosc.fintech.risk.rulemanagement.exception.DataNotFoundException;
import com.smartosc.fintech.risk.rulemanagement.exception.GenerateRuleException;
import com.smartosc.fintech.risk.rulemanagement.exception.TimeInvalidException;
import com.smartosc.fintech.risk.rulemanagement.repository.DataModelGroupRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.RuleSetRepository;
import com.smartosc.fintech.risk.rulemanagement.service.RuleSetService;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.DateTimeMapper;
import com.smartosc.fintech.risk.rulemanagement.service.mapper.RuleSetMapper;
import com.smartosc.fintech.risk.rulemanagement.startup.DataMaster;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class RuleSetServiceImpl implements RuleSetService {
    private final RuleSetRepository ruleSetRepository;
    Environment environment;
    private final UrlStorageConfig urlStorageConfig;
    private final DataModelGroupRepository dataModelGroupRepository;

    @Override
    public ListRuleSetResponse getListRuleSet(Integer page, Integer size, String sort, String sortBy) {
        ListRuleSetResponse listRuleSetResponse = new ListRuleSetResponse();
        Pageable pageable = pageable(page, size, sort, sortBy);
        Page<RuleSetEntity> ruleSetEntities = ruleSetRepository.findAllByStateNot(pageable, StateEnum.DELETE);
        listRuleSetResponse.setPageableData(pageableData(ruleSetEntities, page, size));
        listRuleSetResponse.setRuleSetResponses(RuleSetMapper.mapEntityToResponse(ruleSetEntities, environment.getProperty("id")));
        return listRuleSetResponse;
    }

    @Override
    public List<DeleteRuleSetResponse> deleteRuleSet(DeleteRuleSetRequest deleteRuleSetRequest) {
        List<DeleteRuleSetResponse> deleteRuleSetResponses = new ArrayList<>();
        List<RuleSetEntity> ruleSetEntities = ruleSetRepository.findByIdInAndStateNot(deleteRuleSetRequest.getId(), StateEnum.DELETE).orElseThrow(DataNotFoundException::new);
        for (RuleSetEntity ruleSetEntity : ruleSetEntities) {
            List<RulesResponse> rulesResponses = ruleSetEntity.getRules().stream()
                    .map(RuleSetMapper.INSTANCE::ruleEntityToRuleResponse)
                    .collect(Collectors.toList());
            DeleteRuleSetResponse deleteRuleSetResponse = deleteRuleSetResponse(ruleSetEntity, rulesResponses);
            deleteRuleSetResponses.add(deleteRuleSetResponse);
            isDelete(deleteRuleSetRequest,
                    deleteRuleSetResponse, ruleSetEntity, ruleSetEntities, rulesResponses);
        }
        return deleteRuleSetResponses;
    }

    @Override
    public RuleSetDto createRuleSet(CreateRuleSetRequest createRuleSetRequest) {
        validateDate(createRuleSetRequest.getEffectiveStartDate(), createRuleSetRequest.getEffectiveEndDate());
        GroupModelEntity groupModel = DataMaster.getModelGroupCache().get(createRuleSetRequest.getDataModelGroupId());
        if (checkExistedRuleSet(createRuleSetRequest.getName(), groupModel) != null) {
            throw new DataExistedException(createRuleSetRequest.getName() + " " + ResourceUtil.getMessage(MessageKey.EXISTED));
        }
        RuleSetEntity ruleSetEntity = RuleSetMapper.mapRequestToEntity(groupModel, createRuleSetRequest);
        ruleSetEntity = ruleSetRepository.save(ruleSetEntity);
        return RuleSetMapper.entityToDto(ruleSetEntity, groupModel);
    }


    @Override
    public RuleSetResponse getRuleSetById(Long id) {
        RuleSetEntity ruleSetEntity = ruleSetRepository.findById(id).orElseThrow(DataNotFoundException::new);
        return RuleSetMapper.INSTANCE.mapRuleSetEntityToRuleSetResponse(ruleSetEntity, environment.getProperty("id"));
    }

    @Override
    public List<RuleSetByGroupResponse> getRuleSetByGroup(Long groupId) {

        List<RuleSetByGroupResponse> ruleSetResponses = new ArrayList<>();
        GroupModelEntity dataModelGroup = DataMaster.getModelGroupCache().get(groupId);
        List<RuleSetEntity> ruleSetEntityList = ruleSetRepository.findAllByDataModelGroupAndStateIs(dataModelGroup, StateEnum.ACTIVE);
        for (RuleSetEntity ruleSetEntity : ruleSetEntityList) {
            RuleSetByGroupResponse ruleSetByGroupResponse = new RuleSetByGroupResponse();
            ruleSetByGroupResponse.setId(ruleSetEntity.getId());
            ruleSetByGroupResponse.setName(ruleSetEntity.getName());
            ruleSetResponses.add(ruleSetByGroupResponse);
        }
        return ruleSetResponses;
    }

    @Override
    public UpdateRuleSetResponse updateRuleSet(UpdateRuleSetRequest updateRuleSetRequest) {
        if (!ruleSetRepository.findById(updateRuleSetRequest.getId()).isPresent()) {
            throw new DataNotFoundException();
        }
        UpdateRuleSetResponse updateRuleSetResponse = new UpdateRuleSetResponse();
        GroupModelEntity groupModel = DataMaster.getModelGroupCache().get(updateRuleSetRequest.getDataModelGroupId());
        validateDate(updateRuleSetRequest.getEffectiveStartDate(), updateRuleSetRequest.getEffectiveEndDate());
        RuleSetEntity checkExistedRuleSet = checkExistedRuleSet(updateRuleSetRequest.getName(), groupModel);

        if (checkExistedRuleSet != null && !updateRuleSetRequest.getId().equals(checkExistedRuleSet.getId())) {
            throw new DataExistedException(updateRuleSetRequest.getName() + " " + ResourceUtil.getMessage(MessageKey.EXISTED));
        }

        RuleSetEntity ruleSetEntity = RuleSetMapper.mapRequestUpdateToEntity(groupModel, updateRuleSetRequest);

        JSONObject jsonObjects = convertStringToJson(updateRuleSetRequest);
        List<RulesResponse> listRuleActive = listRule(updateRuleSetRequest);
        if (updateRuleSetRequest.isUpdateConfirm()) {
            ruleSetRepository.save(ruleSetEntity);
            updateRuleSetResponse.setUpdateStatus(StatusEnum.SUCCESS);
            updateRuleSetResponse.setRulesResponseList(listRuleActive);
            updateRuleFile(jsonObjects);
        } else {
            listRuleActive = listRule(updateRuleSetRequest);
            if (listRuleActive.isEmpty()) {
                ruleSetRepository.save(ruleSetEntity);
                updateRuleFile(jsonObjects);
                updateRuleSetResponse.setUpdateStatus(StatusEnum.SUCCESS);
            } else {
                updateRuleSetResponse.setRulesResponseList(listRule(updateRuleSetRequest));
                updateRuleSetResponse.setUpdateStatus(StatusEnum.FALSE);
            }
        }
        return updateRuleSetResponse;
    }

    @Override
    public List<GroupRuleSetResponse> groupRuleSet() {
        List<GroupRuleSetResponse> groupRuleSetResponses = new ArrayList<>();

        List<GroupModelEntity> groupModelEntities = dataModelGroupRepository.findAll();

        for (GroupModelEntity groupModelEntity : groupModelEntities) {
            GroupRuleSetResponse groupRuleSetResponse = new GroupRuleSetResponse();
            groupRuleSetResponse.setName(groupModelEntity.getName());
            List<RuleSetResponseList> ruleSetResponseLists = new ArrayList<>();
            List<RuleSetEntity> ruleSetEntityList = ruleSetRepository.findAllByDataModelGroupAndStateIs(groupModelEntity, StateEnum.ACTIVE);
            for (RuleSetEntity ruleSetEntity : ruleSetEntityList) {
                RuleSetResponseList ruleSetResponseList = new RuleSetResponseList();
                ruleSetResponseList.setId(ruleSetEntity.getId());
                ruleSetResponseList.setName(ruleSetEntity.getName());
                ruleSetResponseLists.add(ruleSetResponseList);
            }
            groupRuleSetResponse.setListRuleSet(ruleSetResponseLists);
            groupRuleSetResponses.add(groupRuleSetResponse);
        }
        return groupRuleSetResponses;
    }


    public RuleSetEntity checkExistedRuleSet(String name, GroupModelEntity groupModelEntity) {
        Optional<RuleSetEntity> ruleSetEntity = ruleSetRepository.findFirstByNameAndDataModelGroupAndStateIs(name, groupModelEntity, StateEnum.ACTIVE);
        return ruleSetEntity.orElse(null);
    }

    public void validateDate(Long start, Long end) {
        Timestamp timeStart = new Timestamp(start);
        Timestamp timeEnd = new Timestamp(end);
        if (timeStart.after(timeEnd) || timeEnd.before(timeStart) || timeEnd.before(new Date())) {
            throw new TimeInvalidException();
        }
    }


    public PageableData pageableData(Page<RuleSetEntity> ruleSetEntities, int page, int size) {
        PageableData pageableData = new PageableData();
        pageableData.setPageNumber(page);
        pageableData.setPageSize(size);
        pageableData.setTotalRecord(ruleSetEntities.getTotalElements());
        pageableData.setTotalPage(ruleSetEntities.getTotalPages());
        return pageableData;
    }

    public List<RulesResponse> listRule(UpdateRuleSetRequest createRuleSetRequest) {
        List<RulesResponse> listRule = new ArrayList<>();
        Optional<RuleSetEntity> ruleSetEntityOptional = ruleSetRepository.findById(createRuleSetRequest.getId());
        RuleSetEntity ruleSetEntity = new RuleSetEntity();
        if (ruleSetEntityOptional.isPresent()) {
            ruleSetEntity = ruleSetEntityOptional.get();
        }
        for (RuleEntity ruleEntity : ruleSetEntity.getRules()) {
            if (ruleEntity.getEffectiveDateStart() == null || ruleEntity.getEffectiveDateEnd() == null) {
                continue;
            }
            if (ruleEntity.getEffectiveDateEnd().after(DateTimeMapper.longToTimestamp(createRuleSetRequest.getEffectiveEndDate()))
                    || ruleEntity.getEffectiveDateStart().before(DateTimeMapper.longToTimestamp(createRuleSetRequest.getEffectiveStartDate()))
                    && ruleEntity.isStatus()) {
                listRule.add(RuleSetMapper.INSTANCE.ruleEntityToRuleResponse(ruleEntity));
            }
        }
        return listRule;
    }

    public DeleteRuleSetResponse deleteRuleSetResponse(RuleSetEntity ruleSetEntity, List<RulesResponse> rulesResponses) {
        DeleteRuleSetResponse deleteRuleSetResponse = new DeleteRuleSetResponse();
        deleteRuleSetResponse.setId(ruleSetEntity.getId());
        deleteRuleSetResponse.setName(ruleSetEntity.getName());
        deleteRuleSetResponse.setListRule(rulesResponses);
        return deleteRuleSetResponse;
    }

    public void isDelete(DeleteRuleSetRequest deleteRuleSetRequest,
                         DeleteRuleSetResponse deleteRuleSetResponse, RuleSetEntity ruleSetEntity, List<RuleSetEntity> ruleSetEntities, List<RulesResponse> rulesResponses) {

        if (!deleteRuleSetRequest.isConfirmDelete() && !rulesResponses.isEmpty()) {
            deleteRuleSetResponse.setStatus(StatusEnum.FALSE);
        } else {
            RuleEngineRequest ruleEngineRequest = buildRequestRuleFile(ruleSetEntity.getId(), ruleSetEntity.getDataModelGroup().getId(), rulesResponses);
            deleteRuleFile(ruleEngineRequest);
            ruleSetEntity.setState(StateEnum.DELETE);
            ruleSetRepository.saveAll(ruleSetEntities);
        }
    }

    Pageable pageable(Integer page, Integer size, String sort, String sortBy) {
        if (sortBy.isEmpty()) {
            sortBy = "id";
        }
        Sort sortable = Sort.by(sortBy).ascending();

        if (sort.equals("DESC")) {
            sortable = Sort.by(sortBy).descending();
        }
        return PageRequest.of(page, size, sortable);
    }

    public void deleteRuleFile(RuleEngineRequest engineRequest) {
        try {
            HttpEntity<RuleEngineRequest> request = new HttpEntity<>(engineRequest);
            ResponseEntity<RuleEngineResponse> response = new RestTemplate().exchange(urlStorageConfig.getRuleengine(), HttpMethod.DELETE, request, RuleEngineResponse.class);
            RuleEngineResponse returnResponse = response.getBody();
            if (returnResponse != null && !ErrorCodeEnum.SUCCESS.getCode().equals(returnResponse.getCode())) {
                throw new GenerateRuleException(returnResponse.getMessage());
            }
        } catch (Exception e) {
            throw new GenerateRuleException(ResourceUtil.getMessage(MessageKey.RULE_ENGINE_ERROR));
        }
    }

    private void updateRuleFile(JSONObject jsonObject) {
        try {
            RuleEngineResponse response = Optional.ofNullable(new RestTemplate().postForObject(urlStorageConfig.getRuleengine(), jsonObject, RuleEngineResponse.class)).orElse(new RuleEngineResponse());
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(response.getCode())) {
                throw new GenerateRuleException(response.getMessage());
            }
        } catch (Exception e) {
            throw new GenerateRuleException(ResourceUtil.getMessage(MessageKey.RULE_ENGINE_ERROR));
        }
    }

    public RuleEngineRequest buildRequestRuleFile(Long ruleSetId, Long modelGroupId, List<RulesResponse> rulesResponses) {
        RuleEngineRequest ruleEngineRequest = new RuleEngineRequest();
        List<BusinessRuleRequest> businessRuleRequests = new ArrayList<>();
        for (RulesResponse rulesResponse : rulesResponses) {
            BusinessRuleRequest businessRuleRequest = new BusinessRuleRequest();
            businessRuleRequest.setRuleId(Long.parseLong(rulesResponse.getId()));
            businessRuleRequest.setDataModelGroup(modelGroupId);
            businessRuleRequest.setRuleSetId(ruleSetId);
            businessRuleRequests.add(businessRuleRequest);
        }
        ruleEngineRequest.setRules(businessRuleRequests);
        return ruleEngineRequest;
    }


    private JSONObject convertStringToJson(UpdateRuleSetRequest updateRuleSetRequest) {

        Optional<RuleSetEntity> ruleSetEntityOptional = ruleSetRepository.findById(updateRuleSetRequest.getId());
        RuleSetEntity ruleSetEntity = new RuleSetEntity();
        if (ruleSetEntityOptional.isPresent()) {
            ruleSetEntity = ruleSetEntityOptional.get();
        }
        JSONObject jsonObject = new JSONObject();
        List<JSONObject> jsonObjects = new ArrayList<>();
        for (RuleEntity ruleEntity : ruleSetEntity.getRules()) {
            JSONObject json = new JSONObject(ruleEntity.getCondition());

            if (ruleEntity.getEffectiveDateEnd() == null) {
                ruleEntity.setEffectiveDateEnd(DateTimeMapper.longToTimestamp(updateRuleSetRequest.getEffectiveEndDate()));
            }
            if (ruleEntity.getEffectiveDateStart() == null) {
                ruleEntity.setEffectiveDateStart(DateTimeMapper.longToTimestamp(updateRuleSetRequest.getEffectiveStartDate()));
            }
            if (updateRuleSetRequest.getEffectiveEndDate() <= DateTimeMapper.timestampToLong(ruleEntity.getEffectiveDateEnd())) {
                json.put("effectiveDateEnd", updateRuleSetRequest.getEffectiveEndDate());
            }
            if (updateRuleSetRequest.getEffectiveStartDate() >= DateTimeMapper.timestampToLong(ruleEntity.getEffectiveDateStart())) {
                json.put("effectiveDateEnd", updateRuleSetRequest.getEffectiveStartDate());
            }
            if (!updateRuleSetRequest.isStatus()) {
                json.put("status", false);
            }
            jsonObjects.add(json);
        }
        jsonObject.put("rules", jsonObjects);
        return jsonObject;
    }

}