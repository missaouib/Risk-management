package com.smartosc.fintech.risk.rulemanagement.dto.response;

import com.smartosc.fintech.risk.rulemanagement.dto.PageableData;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PagingResponse<T> {
    List<T> contents;
    PageableData paging;
}
