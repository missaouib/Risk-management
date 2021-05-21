package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Data;

import java.util.List;

@Data
public class DataModelResponse {
    private List<Long> ids;
    private StatusEnum status;
}
