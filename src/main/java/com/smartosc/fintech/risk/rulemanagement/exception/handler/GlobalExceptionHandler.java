package com.smartosc.fintech.risk.rulemanagement.exception.handler;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.smartosc.fintech.risk.rulemanagement.common.constant.ErrorCode;
import com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey;
import com.smartosc.fintech.risk.rulemanagement.common.util.ResourceUtil;
import com.smartosc.fintech.risk.rulemanagement.dto.response.ErrorResponse;
import com.smartosc.fintech.risk.rulemanagement.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.smartosc.fintech.risk.rulemanagement.common.constant.MessageKey.*;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    public static final String KEY = "error";

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param ex MissingServletRequestParameterException
     * @return the ApiError object
     */

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ErrorResponse<Map<String, String>> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex) {
        log.info("Error MissingServletRequestParameterException", ex);
        String message = String.format(ResourceUtil.getMessage(HANDLE_MISSING_SERVLET_REQUEST_PARAMETER), ex.getParameterName());
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.BAD_REQUEST);
        return error.fail(Collections.singletonMap(KEY, message));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param ex HttpMediaTypeNotSupportedException
     * @return the ApiError object
     */

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ErrorResponse<Map<String, String>> handleHttpMediaTypeNotSupported(
            HttpMediaTypeNotSupportedException ex) {
        log.info("Error HttpMediaTypeNotSupportedException", ex);
        StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(ResourceUtil.getMessage(HANDLE_HTTP_MEDIA_TYPE_NOT_SUPPORTED));
        ex.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        return error.fail(Collections.singletonMap(KEY, builder.substring(0, builder.length() - 2)));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param ex the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse<Map<String, String>> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex) {
        log.info("Error MethodArgumentNotValidException", ex);

        return validationError(ex.getBindingResult());
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ErrorResponse<Map<String, String>> handleTypeMismatch(TypeMismatchException ex) {
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.BAD_REQUEST);
        return error.fail(Collections.singletonMap("error TypeMismatchException", ex.getMessage()));
    }

    @ExceptionHandler(BindException.class)
    public ErrorResponse<Map<String, String>> handleBindException(BindException ex) {
        log.info("Error BindException", ex);

        return validationError(ex.getBindingResult());
    }

    private ErrorResponse<Map<String, String>> validationError(BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        bindingResult.getAllErrors().forEach(error -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (Exception e) {
                fieldName = KEY;
            }
            log.info("InvalidFormatException in field: " + fieldName);
            String errorMessage = error.getDefaultMessage();
            try {
                errorMessage = ResourceUtil.getMessage(errorMessage);
            } catch (Exception e) {
                log.error("cant get message in message data source");
            }
            errors.put(fieldName, errorMessage);
        });
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_METHOD_ARGUMENT_NOT_VALID);
        return error.fail(errors);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param ex the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ErrorResponse<Map<String, String>> handleConstraintViolation(
            javax.validation.ConstraintViolationException ex) {
        log.info("Error ConstraintViolationException", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(constraintViolation -> {
            String field = null;
            for (Path.Node node : constraintViolation.getPropertyPath()) {
                field = node.getName();
            }
            String errorMessage = constraintViolation.getMessage();
            try {
                errorMessage = ResourceUtil.getMessage(errorMessage);
            } catch (Exception e) {
                log.error("cant get message in message data source");
            }
            field = field == null ? KEY : field;
            errors.put(field, errorMessage);
        });
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_CONSTRAINT_VIOLATION);
        return error.fail(errors);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param ex the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ErrorResponse<Map<String, String>> handleJsonInvalidFormat(DataNotFoundException ex) {
        log.info("Error DataNotFoundException", ex);
        return new ErrorResponse<>(ErrorCode.DATA_NOT_FOUND);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param ex HttpMessageNotReadableException
     * @return the ApiError object
     */

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResponse<Map<String, String>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex, WebRequest request) {
        log.info("error HttpMessageNotReadableException", ex);

        if (ex.contains(InvalidFormatException.class) && ex.getRootCause() != null) {
            return handleInvalidFormatException((InvalidFormatException) ex.getRootCause());
        }

        if (ex.contains(EnumNotFoundException.class) && ex.getRootCause() != null) {
            return handleEnumNotFoundException((EnumNotFoundException) ex.getRootCause());
        }

        if (ex.contains(DataInValidException.class) && ex.getRootCause() != null) {
            return handleDataInValidException((DataInValidException) ex.getRootCause());
        }

        if (ex.contains(JsonParseException.class) && ex.getRootCause() != null) {
            return handleJsonParseException((JsonParseException) ex.getRootCause());
        }

        ServletWebRequest servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_CONSTRAINT_VIOLATION);
        return error.fail(Collections
                .singletonMap(KEY, ResourceUtil.getMessage(MessageKey.HANDLE_HTTP_MESSAGE_NOT_READABLE)));
    }

    /**
     * Handle JsonParseException.
     *
     * @param ex JsonParseException
     * @return the ApiError object
     */

    @ExceptionHandler(InvalidFormatException.class)
    public ErrorResponse<Map<String, String>> handleInvalidFormatException(InvalidFormatException ex) {
        log.info("InvalidFormatException");
        String value = ex.getValue().toString();
        String field = ex.getPath().get(ex.getPath().size() - 1).getFieldName();
        String type = ex.getTargetType().getSimpleName();
        DataInValidException e = new DataInValidException(field, value, type);
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_CONSTRAINT_VIOLATION);
        return error.fail(Collections.singletonMap(e.getField(), e.getMessage()));

    }

    /**
     * Handle JsonParseException.
     *
     * @param ex JsonParseException
     * @return the ApiError object
     */

    @ExceptionHandler(JsonParseException.class)
    public ErrorResponse<Map<String, String>> handleJsonParseException(JsonParseException ex) {
        log.info("JsonParseException");
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_CONSTRAINT_VIOLATION);
        JsonStreamContext jsc = ex.getProcessor().getParsingContext();
        String name = ObjectUtils.isEmpty(jsc.getCurrentName()) ? jsc.getParent().getCurrentName() : jsc.getCurrentName();
        return error.fail(Collections.singletonMap(ObjectUtils.isEmpty(name) ? KEY : name,
                ResourceUtil.getMessage(MessageKey.HANDLE_HTTP_MESSAGE_NOT_READABLE)));
    }

    /**
     * Handle DataInValidException.
     *
     * @param ex DataInValidException
     * @return the ApiError object
     */

    @ExceptionHandler(DataInValidException.class)
    public ErrorResponse<Map<String, String>> handleDataInValidException(DataInValidException ex) {
        log.info("DataInValidException");
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_CONSTRAINT_VIOLATION);
        return ex == null ? error : error.fail(Collections.singletonMap(ex.getField(), ex.getMessage()));
    }

    /**
     * Handle EnumNotFoundException.
     *
     * @param ex EnumNotFoundException
     * @return the ApiError object
     */

    @ExceptionHandler(EnumNotFoundException.class)
    public ErrorResponse<Map<String, String>> handleEnumNotFoundException(EnumNotFoundException ex) {
        log.info("EnumNotFoundException");
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.HANDLE_CONSTRAINT_VIOLATION);
        return ex == null ? error : error.fail(Collections.singletonMap(ex.getField(), ex.getMessage()));
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param ex HttpMessageNotWritableException
     * @return the ApiError object
     */

    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ErrorResponse<Map<String, String>> handleHttpMessageNotWritable(
            HttpMessageNotWritableException ex) {
        log.info("Error HttpMessageNotWritableException", ex);
        return new ErrorResponse<>(ErrorCode.HANDLE_HTTP_MESSAGE_NOT_WRITABLE);
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param ex NoHandlerFoundException
     * @return the ApiError object
     */

    @ExceptionHandler(NoHandlerFoundException.class)
    public ErrorResponse<Map<String, String>> handleNoHandlerFoundException(
            NoHandlerFoundException ex) {
        log.info("Error NoHandlerFoundException", ex);
        String message = String.format(ResourceUtil.getMessage(HANDLE_NOT_FOUND_EXCEPTION), ex.getHttpMethod(), ex.getRequestURL());
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.URL_NOT_FOUND);
        return error.fail(Collections.singletonMap(KEY, message));
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param ex the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorResponse<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        log.info("Error DataIntegrityViolationException", ex);
        if (ex.getCause() instanceof ConstraintViolationException) {
            return new ErrorResponse<>(ErrorCode.HANDLE_DATA_INTEGRITY_VIOLATION);
        }
        return new ErrorResponse<>(ErrorCode.INTERNAL_SERVER_ERROR);
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param ex the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorResponse<Map<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        log.info("Error MethodArgumentTypeMismatchException", ex);
        String message = String.format(ResourceUtil.getMessage(HANDLE_METHOD_ARGUMENT_TYPE_MISMATCH),
                ex.getName(), ex.getValue(), Objects.requireNonNull(ex.getRequiredType()).getSimpleName());
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.BAD_REQUEST);
        return error.fail(Collections.singletonMap(KEY, message));
    }

    @ExceptionHandler(BusinessServiceException.class)
    public ErrorResponse<Map<String, String>> handleBusinessService(BusinessServiceException ex) {
        log.info("Error BusinessServiceException", ex);
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.INTERNAL_SERVER_ERROR);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ErrorResponse<Map<String, String>> handleInternalException(Exception ex) {
        log.info("Error Exception", ex);
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.INTERNAL_SERVER_ERROR);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorResponse<Map<String, String>> handleInternalException(HttpRequestMethodNotSupportedException ex) {
        log.info("Error Exception", ex);
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.METHOD_NOT_SUPPORT);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

    @ExceptionHandler(NameAttributeDuplicateException.class)
    public ErrorResponse<Map<String, String>> handleGroupModelChangeException(NameAttributeDuplicateException ex) {
        log.info("Error GroupModelChangeException", ex);
        return new ErrorResponse<Map<String, String>>(ErrorCode.BAD_REQUEST)
                .fail(Collections.singletonMap(KEY, ResourceUtil.getMessage(MessageKey.NAME_ATTRIBUTE_DUPLICATE)));

    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public ErrorResponse<Map<String, String>> handleAttributeNotFoundException(AttributeNotFoundException ex) {
        log.info("Error AttributeNotFoundException", ex);
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.DATA_NOT_FOUND);

        String message = String.format(ResourceUtil.getMessage(ATTRIBUTE_NOT_FOUND_IN_MODEL),
                ex.getAttributeIds());
        return error.fail(Collections.singletonMap(KEY, message));

    }


    @ExceptionHandler(DataExistedException.class)
    public ErrorResponse<Map<String, String>> handleInternalException(DataExistedException ex) {
        log.info("Data Existed Exception", ex);
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.DATA_EXISTED);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

    @ExceptionHandler(ConnectionErrorException.class)
    public ErrorResponse<Map<String, String>> handleConnectionException(ConnectionErrorException ex) {
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.CONNECTION_ERROR);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

    @ExceptionHandler(TimeInvalidException.class)
    public ErrorResponse<Map<String, String>> handleTimeException(TimeInvalidException ex) {
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.TIME_INVALID);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

    @ExceptionHandler(JsonFormatException.class)
    public ErrorResponse<Map<String, String>> handleJsonInvalidFormat(JsonFormatException ex) {
        return new ErrorResponse<>(ErrorCode.JSON_FORMAT_ERROR);
    }

    @ExceptionHandler(GenerateRuleException.class)
    public ErrorResponse<Map<String, String>> handleGenerateRuleException(GenerateRuleException ex) {
        ErrorResponse<Map<String, String>> error = new ErrorResponse<>(ErrorCode.BAD_REQUEST);
        return error.fail(Collections.singletonMap(KEY, ex.getMessage()));
    }

}
