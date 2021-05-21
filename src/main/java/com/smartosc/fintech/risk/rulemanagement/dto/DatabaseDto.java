package com.smartosc.fintech.risk.rulemanagement.dto;

import com.smartosc.fintech.risk.rulemanagement.entity.DatabaseEntity;
import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DatabaseDto {
    List<DatabaseEntity> driverList = new ArrayList<>();
    List<GroupModelEntity> groupList = new ArrayList<>();
}
