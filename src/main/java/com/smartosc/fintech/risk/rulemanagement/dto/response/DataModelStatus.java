package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.enumeration.StatusEnum;
import lombok.Data;

@Data
public class DataModelStatus {
    private String modelName;
    private String groupName;
    private StatusEnum status;
}
