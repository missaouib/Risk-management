package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;

import java.util.Map;

public interface ModelGroupService {
    Map<Long, GroupModelEntity> getAllModelGroup();
}
