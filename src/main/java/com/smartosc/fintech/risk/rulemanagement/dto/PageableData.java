package com.smartosc.fintech.risk.rulemanagement.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PageableData {
    int pageNumber;
    int pageSize;
    int totalPage;
    long totalRecord;
}
