package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.DatabaseDto;
import com.smartosc.fintech.risk.rulemanagement.entity.ObjectModel;

import java.sql.SQLException;
import java.util.List;

public interface DatabaseService {
    DatabaseDto getAllDatabaseInfo();

    List<ObjectModel> showListTable() throws SQLException;


}
