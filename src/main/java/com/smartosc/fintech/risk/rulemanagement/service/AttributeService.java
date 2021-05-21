package com.smartosc.fintech.risk.rulemanagement.service;

import com.smartosc.fintech.risk.rulemanagement.dto.AttributeDto;
import com.smartosc.fintech.risk.rulemanagement.dto.response.AttributeResponse;

import java.util.List;

public interface AttributeService {

    void deleteAttribute(Long dataModelId, Long attributeId);

    AttributeResponse updateAttribute(Long dataModelId, Long attributeId, AttributeDto attributeDto);

    void deleteMultiAttribute(Long dataModelId, List<Long> ids);
}
