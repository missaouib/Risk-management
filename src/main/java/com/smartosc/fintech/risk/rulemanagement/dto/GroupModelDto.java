package com.smartosc.fintech.risk.rulemanagement.dto;

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
public class GroupModelDto {
    private String id;
    private String name;
    private String code;
    private String description;
}
