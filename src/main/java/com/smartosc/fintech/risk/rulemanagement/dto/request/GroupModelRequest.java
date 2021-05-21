package com.smartosc.fintech.risk.rulemanagement.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @Author vuhq
 * @Since 2/24/2021
 */
@Getter
@Setter
@NoArgsConstructor
public class GroupModelRequest {
    private String id;
    private String name;
    private String code;
    private String description;
}
