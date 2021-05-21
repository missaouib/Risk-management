package com.smartosc.fintech.risk.rulemanagement.controller;

import com.smartosc.fintech.risk.rulemanagement.dto.DatabaseDto;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DBSettings;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import com.smartosc.fintech.risk.rulemanagement.entity.ObjectModel;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RequestMapping(path = "/${application-short-name}/${version}/")
@Api(value = "User API")
public interface DatabaseController {

    @CrossOrigin
    @GetMapping(value = "/categories")
    SuccessResponse<DatabaseDto> getAllDatabaseInfo();

    @CrossOrigin
    @GetMapping(value = "/data-models/pull")
    SuccessResponse<List<ObjectModel>> getAllTable() throws SQLException;

    @CrossOrigin
    @PostMapping(value = "/data-models/setting", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    SuccessResponse<DBSettings> setDatabaseSettings(@RequestBody DBSettings dbSettings);

}
