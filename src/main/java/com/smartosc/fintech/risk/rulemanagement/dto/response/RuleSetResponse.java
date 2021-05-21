package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.entity.GroupModelEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RuleSetResponse {

     private String displayId;
    private Long id;

    private String name;

    private Long effectiveStartDate;

    private Long effectiveEndDate;

    private boolean status;

    private List<RuleDto> listRule = new ArrayList<>();

    private GroupModelEntity dataModelGroup;

}
