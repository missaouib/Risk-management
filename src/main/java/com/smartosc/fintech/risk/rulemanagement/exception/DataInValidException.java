package com.smartosc.fintech.risk.rulemanagement.exception;

import com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey;
import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import lombok.Getter;

@Getter
public class DataInValidException extends RuntimeException {
    private static final long serialVersionUID = -8880008994186415872L;

    private final String field;

    public DataInValidException(String field, String value, String type) {
        super(String.format(ResourceUtil.getMessage(MessageKey.DATA_NOT_VALID_FORMAT),
                value,
                type));
        this.field = field;
    }
}
