package com.smartosc.fintech.risk.rulemanagement.exception;

import com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey;
import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import lombok.Getter;

/**
 * @Author vuhq
 * @Since 2/17/2021
 */
@Getter
public class EnumNotFoundException extends RuntimeException {
    private static final long serialVersionUID = -4292066769296408376L;

    private final String field;

    public EnumNotFoundException(String message, String value, String data) {
        super(String.format(ResourceUtil.getMessage(MessageKey.ENUM_NOT_FOUND),
                value,
                data));
        this.field = message;
    }

}
