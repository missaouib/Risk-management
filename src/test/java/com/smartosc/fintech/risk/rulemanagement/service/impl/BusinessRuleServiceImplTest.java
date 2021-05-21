package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartosc.fintech.risk.rulemanagement.config.UrlStorageConfig;
import com.smartosc.fintech.risk.rulemanagement.dto.request.BusinessRuleRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.RuleDetailResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.RuleEntity;
import com.smartosc.fintech.risk.rulemanagement.repository.AttributeRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.RuleRepository;
import com.smartosc.fintech.risk.rulemanagement.repository.RuleSetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BusinessRuleServiceImplTest {

    @InjectMocks
    BusinessRuleServiceImpl businessRuleService;
    RuleSetRepository ruleSetRepository;
    RuleRepository ruleRepository;
    AttributeRepository attributeRepository;
    UrlStorageConfig urlStorageConfig;

    @BeforeEach
    void setUp() {
        ruleSetRepository = mock(RuleSetRepository.class);
        ruleRepository = spy(RuleRepository.class);
        attributeRepository = mock((AttributeRepository.class));
        businessRuleService = new BusinessRuleServiceImpl(ruleSetRepository, ruleRepository, attributeRepository, urlStorageConfig);
    }

//    @Test
//    public void testSaveRuleSuccess() {
//        RuleSetEntity ruleSetEntity = new RuleSetEntity();
//        ruleSetEntity.setId(100L);
//        when(ruleSetRepository.findById(anyLong())).thenReturn(Optional.of(ruleSetEntity));
//
//        AttributeEntity attributeEntity = new AttributeEntity();
//        attributeEntity.setId(100L);
//        when(attributeRepository.findById(anyLong())).thenReturn(Optional.of(attributeEntity));
//
//        RuleEntity ruleEntity = new RuleEntity();
//        ruleEntity.setId(100L);
//        ruleEntity.setName("TestSaveRule");
//        when(ruleRepository.save(any(RuleEntity.class))).thenReturn(ruleEntity);
//
//        BusinessRuleResponse response = businessRuleService.createBusinessRule(initRequest());
//
//        assertThat(response.getRuleId()).isEqualTo(100L);
//
//    }

    @Test
    public void testGetRuleDetail() {
        RuleEntity ruleEntity = new RuleEntity();
        ruleEntity.setId(100L);
        ruleEntity.setName("TestSaveRule");
        ruleEntity.setCondition(getResourceFileAsString());
        when(ruleRepository.findRuleEntityByIdAndStateNot(anyLong(), any())).thenReturn(Optional.of(ruleEntity));
        RuleDetailResponse response = businessRuleService.getRuleDetail(100L);
        assertThat(response.getRuleId()).isEqualTo(100L);
    }

    public BusinessRuleRequest initRequest() {
        BusinessRuleRequest request = new BusinessRuleRequest();
        try {
            request = new ObjectMapper().readValue(getResourceFileAsString(), BusinessRuleRequest.class);
        } catch (JsonProcessingException e) {
            fail("Wrong JSON format");
        }
        return request;
    }

    private String getResourceFileAsString() {
        InputStream is = getResourceFileAsInputStream();
        if (is != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        } else {
            fail("Don't have file");
            return "";
        }
    }

    private InputStream getResourceFileAsInputStream() {
        ClassLoader classLoader = BusinessRuleServiceImplTest.class.getClassLoader();
        return classLoader.getResourceAsStream("request.json");
    }
}
