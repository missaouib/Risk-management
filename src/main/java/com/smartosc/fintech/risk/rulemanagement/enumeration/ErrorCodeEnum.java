package com.smartosc.fintech.risk.rulemanagement.enumeration;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    SUCCESS("RSK-RE-200"),
    SERVER_ERROR("RSK-RE-500"),
    CONNECTION_TIMEOUT("RSK-RE-504"),
    CONFLICT("RSK-RE-409"),
    BAD_REQUEST("RSK-RE-400"),
    UNSUPPORTED_MEDIA_TYPE("RSK-RE-415"),
    NOT_FOUND("RSK-RE-404"),
    METHOD_NOT_SUPPORT("RSK-RE-405"),
    DATA_TYPE_INVALID("RSK-RE-400");

    private String code;

    ErrorCodeEnum(String code) {
        this.code = code;
    }
}
