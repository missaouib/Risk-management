package com.smartosc.fintech.risk.rulemanagement.controller.impl;

import com.smartosc.fintech.risk.rulemanagement.config.DBContextHolder;
import com.smartosc.fintech.risk.rulemanagement.controller.DatabaseController;
import com.smartosc.fintech.risk.rulemanagement.dto.DatabaseDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DBSettings;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.ObjectModel;
import com.smartosc.fintech.risk.rulemanagement.enumeration.DBTypeEnum;
import com.smartosc.fintech.risk.rulemanagement.service.DatabaseService;
import com.smartosc.fintech.risk.rulemanagement.service.IDBSettingSwitcherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@AllArgsConstructor
public class DatabaseControllerImpl implements DatabaseController {
    private final DatabaseService databaseService;
    private final IDBSettingSwitcherService idbSettingSwitcherServiceImpl;

    @Override
    public SuccessResponse<DatabaseDto> getAllDatabaseInfo() {
        return SuccessResponse.<DatabaseDto>instance().data(databaseService.getAllDatabaseInfo());
    }

    @Override
    public SuccessResponse<List<ObjectModel>> getAllTable() throws SQLException {
        DBContextHolder.setCurrentDb(DBTypeEnum.NEW);
        return SuccessResponse.<List<ObjectModel>>instance().data(databaseService.showListTable());
    }

    @Override
    public SuccessResponse setDatabaseSettings(@Valid DBSettings dbSettings) {
        idbSettingSwitcherServiceImpl.applySettings(dbSettings);
        return SuccessResponse.<Void>instance();
    }


}
