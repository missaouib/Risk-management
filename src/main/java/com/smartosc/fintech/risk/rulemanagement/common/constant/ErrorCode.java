package com.smartosc.fintech.risk.rulemanagement.common.constant;

import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import com.smartosc.fintech.risk.rulemanagement.dto.response.BaseResponse;
import com.smartosc.fintech.risk.rulemanagement.enumeration.ErrorCodeEnum;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorCode {
    // Define business exception code from 1000
    public static final int EMAIL_EMPTY_ERROR = 1020;

    public static final BaseResponse SUCCESS = new BaseResponse(ResourceUtil.getMessage(MessageKey.APP_RESPONSE_OK), ErrorCodeEnum.SUCCESS.getCode());

    public static final BaseResponse HANDLE_DATA_INTEGRITY_VIOLATION = new BaseResponse(ResourceUtil.getMessage(MessageKey.HANDLE_DATA_INTEGRITY_VIOLATION), ErrorCodeEnum.CONFLICT.getCode());

    public static final BaseResponse INTERNAL_SERVER_ERROR = new BaseResponse(ResourceUtil.getMessage(MessageKey.INTERNAL_SERVER_ERROR), ErrorCodeEnum.SERVER_ERROR.getCode());

    public static final BaseResponse DATA_NOT_FOUND = new BaseResponse(ResourceUtil.getMessage(MessageKey.DATA_NOT_FOUND), ErrorCodeEnum.NOT_FOUND.getCode());

    public static final BaseResponse BAD_REQUEST = new BaseResponse(ResourceUtil.getMessage(MessageKey.BAD_REQUEST), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse UNSUPPORTED_MEDIA_TYPE = new BaseResponse(ResourceUtil.getMessage(MessageKey.HANDLE_HTTP_MEDIA_TYPE_NOT_SUPPORTED), ErrorCodeEnum.UNSUPPORTED_MEDIA_TYPE.getCode());

    public static final BaseResponse HANDLE_METHOD_ARGUMENT_NOT_VALID = new BaseResponse(ResourceUtil.getMessage(MessageKey.HANDLE_METHOD_ARGUMENT_NOT_VALID), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse HANDLE_CONSTRAINT_VIOLATION = new BaseResponse(ResourceUtil.getMessage(MessageKey.HANDLE_CONSTRAINT_VIOLATION), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse HANDLE_HTTP_MESSAGE_NOT_READABLE = new BaseResponse(ResourceUtil.getMessage(MessageKey.HANDLE_HTTP_MESSAGE_NOT_READABLE), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse HANDLE_HTTP_MESSAGE_NOT_WRITABLE = new BaseResponse(ResourceUtil.getMessage(MessageKey.HANDLE_HTTP_MESSAGE_NOT_WRITABLE), ErrorCodeEnum.SERVER_ERROR.getCode());

    public static final BaseResponse METHOD_NOT_SUPPORT = new BaseResponse(ResourceUtil.getMessage(MessageKey.METHOD_NOT_SUPPORT), ErrorCodeEnum.METHOD_NOT_SUPPORT.getCode());

    public static final BaseResponse DATA_TYPE_INVALID = new BaseResponse(ResourceUtil.getMessage(MessageKey.DATA_TYPE_INVALID), ErrorCodeEnum.DATA_TYPE_INVALID.getCode());

    public static final BaseResponse URL_NOT_FOUND = new BaseResponse(ResourceUtil.getMessage(MessageKey.URL_NOT_FOUND), ErrorCodeEnum.NOT_FOUND.getCode());

    public static final BaseResponse DATA_EXISTED = new BaseResponse(ResourceUtil.getMessage(MessageKey.DATA_EXISTED), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse GROUP_MODEL_NOT_MATCH = new BaseResponse(ResourceUtil.getMessage(MessageKey.NAME_ATTRIBUTE_DUPLICATE), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse CONNECTION_ERROR = new BaseResponse(ResourceUtil.getMessage(MessageKey.CONNECTION_ERROR), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse TIME_INVALID = new BaseResponse(ResourceUtil.getMessage(MessageKey.TIME_INVALID), ErrorCodeEnum.BAD_REQUEST.getCode());

    public static final BaseResponse JSON_FORMAT_ERROR = new BaseResponse(ResourceUtil.getMessage(MessageKey.JSON_FORMAT), ErrorCodeEnum.BAD_REQUEST.getCode());

}
