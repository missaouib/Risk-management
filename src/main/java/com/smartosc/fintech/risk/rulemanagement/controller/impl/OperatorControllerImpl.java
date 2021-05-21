package com.smartosc.fintech.risk.rulemanagement.controller.impl;

import com.smartosc.fintech.risk.rulemanagement.controller.OperatorController;
import com.smartosc.fintech.risk.rulemanagement.dto.response.OperatorResponse;
import com.smartosc.fintech.risk.rulemanagement.dto.response.SuccessResponse;
import com.smartosc.fintech.risk.rulemanagement.service.OperatorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class OperatorControllerImpl implements OperatorController {
    OperatorService operatorService;

    @Override
    public SuccessResponse<List<OperatorResponse>> getAllOperators() {
        return SuccessResponse
                .<List<OperatorResponse>>instance()
                .data(operatorService.getAllOperator());
    }
}
