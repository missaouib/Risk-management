package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey;
import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import com.smartosc.fintech.risk.rulemanagement.config.UrlStorageConfig;
import com.smartosc.fintech.risk.rulemanagement.dto.PageableData;
import com.smartosc.fintech.risk.rulemanagement.dto.request.*;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;
import com.smartosc.fintech.risk.rulemanagement.entity.AttributeEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleSetEntity;
import com.smartosc.fintech.risk.rulemanagement.enumeration.ErrorCodeEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StateEnum;
import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import com.smartosc.fintech.risk.rulemanagement.exception.DataExistedException;
import com.smartosc.fintech.risk.rulemanagement.exception.DataNotFoundException;
import com.smartosc.fintech.risk.rulemanagement.exception.GenerateRuleException;
import com.smartosc.fintech.risk.rulemanagement.exception.JsonFormatException;
import com.smartosc.fintech.risk.rulemanagement.repository.AttributeRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.RuleRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.RuleSetRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.specification.RuleSpecification;
import com.smartosc.fintech.risk.rulemanagement.service.BusinessRuleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j

public class BusinessRuleServiceImpl implements BusinessRuleService {

    private final RuleSetRepository ruleSetRepository;
    private final RuleRepository ruleRepository;
    private final AttributeRepository attributeRepository;
    private final UrlStorageConfig urlStorageConfig;

    @Override
    @Transactional(rollbackFor = GenerateRuleException.class)
    public BusinessRuleResponse createBusinessRule(BusinessRuleRequest requestBody) {
        BusinessRuleResponse response = new BusinessRuleResponse();
        //Insert rule entity
        RuleEntity ruleEntity = new RuleEntity();
        if (ruleRepository.existsRuleEntityByNameAndStateNot(requestBody.getRuleName(), StateEnum.DELETE)) {
            throw new DataExistedException(requestBody.getRuleName() + " " + ResourceUtil.getMessage(MessageKey.EXISTED));
        }
        RuleSetEntity ruleSetEntity = ruleSetRepository.findByIdAndStateNot(requestBody.getRuleSetId(), StateEnum.DELETE).orElseThrow(DataNotFoundException::new);
        requestBody.setRuleSetName(ruleSetEntity.getName());
        saveRule(ruleEntity, ruleSetEntity, requestBody);
        requestBody.setRuleId(ruleEntity.getId());
        setEffectiveDate(ruleSetEntity, requestBody);
        generateRuleFile(requestBody);
        response.setRuleId(ruleEntity.getId());
        return response;
    }

    @Override
    public RuleDetailResponse getRuleDetail(Long ruleId) {
        RuleEntity ruleEntity = ruleRepository.findRuleEntityByIdAndStateNot(ruleId, StateEnum.DELETE).orElseThrow(DataNotFoundException::new);
        RuleDetailResponse response = new RuleDetailResponse();
        response.setRuleId(ruleEntity.getId());
        ObjectMapper mapper = new ObjectMapper();
        try {
            response.setRuleDetail(mapper.readTree(ruleEntity.getCondition()));
        } catch (JsonProcessingException e) {
            throw new JsonFormatException();
        }
        return response;
    }

    @Override
    public PagingResponse<RuleListResponse> getListRule(ListBusinessRuleRequest request) {
        Pageable pageable = pageable(request.getPage(), request.getSize(), request.getSort(), request.getSortBy());
        Specification<RuleEntity> specification = RuleSpecification.spec()
                .withNotState(StateEnum.DELETE)
                .withRuleSetId(request.getRuleSetId())
                .build();
        Page<RuleEntity> ruleEntities = ruleRepository.findAll(specification, pageable);
        PagingResponse<RuleListResponse> pageResponse = new PagingResponse<>();
        pageResponse.setPaging(pageableData(ruleEntities, request.getPage(), request.getSize()));
        pageResponse.setContents(setListResponse(ruleEntities));
        return pageResponse;
    }

    @Override
    @Transactional(rollbackFor = GenerateRuleException.class)
    public List<DeleteRuleResponse> deleteBusinessRule(DeleteBusinessRuleRequest request) {
        List<DeleteRuleResponse> responses = new ArrayList<>();
        List<RuleEntity> ruleEntities = ruleRepository.findByIdInAndStateNot(request.getIds(), StateEnum.DELETE).orElseThrow(DataNotFoundException::new);
        List<BusinessRuleRequest> requestList = new ArrayList<>();
        List<RuleEntity> saveEntity = new ArrayList<>();
        for (RuleEntity entity : ruleEntities) {
            //Set request to rule engine
            BusinessRuleRequest ruleRequest = new BusinessRuleRequest();
            ruleRequest.setDataModelGroup(entity.getRuleSet().getDataModelGroup().getId());
            ruleRequest.setRuleSetId(entity.getRuleSet().getId());
            ruleRequest.setRuleSetName(entity.getRuleSet().getName());
            ruleRequest.setRuleId(entity.getId());
            ruleRequest.setRuleName(entity.getName());
            requestList.add(ruleRequest);
            //Update state of rule in db
            entity.setState(StateEnum.DELETE);
            saveEntity.add(entity);
            //Set response
            DeleteRuleResponse response = new DeleteRuleResponse();
            response.setId(entity.getId());
            response.setName(entity.getName());
            response.setStatus(StatusEnum.SUCCESS);
            responses.add(response);
        }
        RuleEngineRequest engineRequest = new RuleEngineRequest();
        engineRequest.setRules(requestList);
        deleteRuleFile(engineRequest);
        ruleRepository.saveAll(saveEntity);
        return responses;
    }

    @Override
    @Transactional(rollbackFor = GenerateRuleException.class)
    public BusinessRuleResponse updateBusinessRule(Long ruleId, BusinessRuleRequest requestBody) {
        RuleEntity ruleEntity = ruleRepository.findRuleEntityByIdAndStateNot(ruleId, StateEnum.DELETE).orElseThrow(DataNotFoundException::new);
        if (!ruleEntity.getName().equals(requestBody.getRuleName()) && ruleRepository.existsRuleEntityByNameAndStateNot(requestBody.getRuleName(), StateEnum.DELETE)) {
            throw new DataExistedException(requestBody.getRuleName() + " " + ResourceUtil.getMessage(MessageKey.EXISTED));
        }
        RuleSetEntity ruleSetEntity = ruleSetRepository.findByIdAndStateNot(requestBody.getRuleSetId(), StateEnum.DELETE).orElseThrow(DataNotFoundException::new);
        requestBody.setRuleSetName(ruleSetEntity.getName());
        BusinessRuleResponse response = new BusinessRuleResponse();
        saveRule(ruleEntity, ruleSetEntity, requestBody);
        setEffectiveDate(ruleSetEntity, requestBody);
        requestBody.setRuleId(ruleEntity.getId());
        generateRuleFile(requestBody);
        response.setRuleId(ruleEntity.getId());
        return response;
    }

    private void saveRule(RuleEntity ruleEntity, RuleSetEntity ruleSetEntity, BusinessRuleRequest requestBody) {
        ruleEntity.setRuleSet(ruleSetEntity);
        ruleEntity.setEffectiveDateStart(requestBody.getEffectiveDateStart());
        ruleEntity.setEffectiveDateEnd(requestBody.getEffectiveDateEnd());
        ruleEntity.setName(requestBody.getRuleName());
        ruleEntity.setRuleType(requestBody.getRuleType());
        ruleEntity.setStatus(requestBody.isStatus());
        ruleEntity.setState(StateEnum.ACTIVE);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String ruleJson;
        try {
            ruleJson = ow.writeValueAsString(requestBody);
        } catch (JsonProcessingException e) {
            throw new JsonFormatException();
        }
        ruleEntity.setCondition(ruleJson);
        Set<Long> attributeIds = new HashSet<>();
        List<AttributeEntity> attributeEntityList = new ArrayList<>();
        for (ChildRuleListRequest rule : requestBody.getListRule()) {
            if (null != rule.getDataModels()) {
                for (DataModelCreateRuleRequest dataModel : rule.getDataModels()) {
                    for (AttributeCreateRuleRequest attribute : dataModel.getAttributes()) {
                        attributeIds.add(attribute.getId());
                    }
                }
            }
        }
        for (Long id : attributeIds) {
            if (id == -1) {
                continue;
            }
            AttributeEntity attributeEntity = attributeRepository.findById(id).orElseThrow(DataNotFoundException::new);
            attributeEntityList.add(attributeEntity);
        }
        ruleEntity.setDataAttributes(attributeEntityList);
        ruleRepository.save(ruleEntity);
    }

    private Pageable pageable(Integer page, Integer size, String sort, String sortBy) {
        if (sortBy.isEmpty()) {
            sortBy = "created_date";
        }
        Sort sortable = sort.equals("DESC") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        return PageRequest.of(page - 1, size, sortable);
    }

    private PageableData pageableData(Page<RuleEntity> ruleEntities, int page, int size) {
        PageableData pageableData = new PageableData();
        pageableData.setPageNumber(page);
        pageableData.setPageSize(size);
        pageableData.setTotalRecord(ruleEntities.getTotalElements());
        pageableData.setTotalPage(ruleEntities.getTotalPages());
        return pageableData;
    }

    private List<RuleListResponse> setListResponse(Page<RuleEntity> entities) {
        List<RuleListResponse> responses = new LinkedList<>();
        for (RuleEntity entity : entities) {
            RuleListResponse response = new RuleListResponse();
            response.setRuleId(entity.getId());
            response.setRuleName(entity.getName());
            if (entity.getEffectiveDateStart() != null) {
                response.setEffectiveDateStart(entity.getEffectiveDateStart().getTime());
            }
            response.setRuleSetId(entity.getRuleSet().getId());
            response.setRuleSetName(entity.getRuleSet().getName());
            response.setGroupModelId(entity.getRuleSet().getDataModelGroup().getId());
            response.setGroupModelName(entity.getRuleSet().getDataModelGroup().getName());
            response.setStatus(entity.getState());
            responses.add(response);
        }
        return responses;
    }

    private void generateRuleFile(BusinessRuleRequest request) {
        RuleEngineRequest ruleEngineRequest = new RuleEngineRequest();
        List<BusinessRuleRequest> requestList = new ArrayList<>();
        requestList.add(request);
        ruleEngineRequest.setRules(requestList);
        try {
            RuleEngineResponse response = Optional.ofNullable(new RestTemplate().postForObject(urlStorageConfig.getRuleengine(), ruleEngineRequest, RuleEngineResponse.class)).orElse(new RuleEngineResponse());
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(response.getCode())) {
                throw new GenerateRuleException(response.getMessage());
            }
        } catch (Exception e) {
            throw new GenerateRuleException(ResourceUtil.getMessage(MessageKey.RULE_ENGINE_ERROR));
        }
    }

    private void deleteRuleFile(RuleEngineRequest engineRequest) {
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

    private void setEffectiveDate(RuleSetEntity ruleSetEntity, BusinessRuleRequest requestBody) {
        if (ruleSetEntity.getEffectiveDateStart() != null) {
            if (requestBody.getEffectiveDateStart() != null) {
                if (ruleSetEntity.getEffectiveDateStart().after(requestBody.getEffectiveDateStart())) {
                    requestBody.setEffectiveDateStart(ruleSetEntity.getEffectiveDateStart());
                }
            } else {
                requestBody.setEffectiveDateStart(ruleSetEntity.getEffectiveDateStart());
            }
        }

        if (ruleSetEntity.getEffectiveDateEnd() != null) {
            if (requestBody.getEffectiveDateEnd() != null) {
                if (ruleSetEntity.getEffectiveDateEnd().before(requestBody.getEffectiveDateEnd())) {
                    requestBody.setEffectiveDateEnd(ruleSetEntity.getEffectiveDateEnd());
                }
            } else {
                requestBody.setEffectiveDateEnd(ruleSetEntity.getEffectiveDateEnd());
            }
        }

        if (!ruleSetEntity.isStatus()) {
            requestBody.setStatus(false);
        }
    }
}
