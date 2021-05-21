package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.request.CreateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.DeleteRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.request.UpdateRuleSetRequest;
import com.smartosc.fintech.risk.rulemanagement.dto.response.*;

import java.util.List;
import java.util.Map;

public interface RuleSetService {
    RuleSetDto createRuleSet(CreateRuleSetRequest createRuleSetRequest);

    ListRuleSetResponse getListRuleSet(Integer page, Integer size, String sort, String sortBy);

    List<DeleteRuleSetResponse> deleteRuleSet(DeleteRuleSetRequest deleteRuleSetRequest);

    RuleSetResponse getRuleSetById(Long id);

    List<RuleSetByGroupResponse> getRuleSetByGroup(Long groupId);

    UpdateRuleSetResponse updateRuleSet(UpdateRuleSetRequest updateRuleSetRequest);

    List<GroupRuleSetResponse> groupRuleSet();


}
