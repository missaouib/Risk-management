package com.smartosc.fintech.risk.rulemanagement.service.impl;

import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import com.smartosc.fintech.risk.rulemanagement.repository.DataModelGroupRepository;
import com.smartosc.fintech.risk.rulemanagement.service.ModelGroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@AllArgsConstructor
public class ModelGroupServiceImpl implements ModelGroupService {

    private final DataModelGroupRepository dataModelGroupRepository;

    @Override
    public Map<Long, GroupModelEntity> getAllModelGroup() {
        List<GroupModelEntity> listGroup = dataModelGroupRepository.findAll();
        Map<Long, GroupModelEntity> mapGroup = new HashMap<>();
        listGroup.forEach(modelGroup -> mapGroup.put(modelGroup.getId(), modelGroup));
        return mapGroup;
    }
}
