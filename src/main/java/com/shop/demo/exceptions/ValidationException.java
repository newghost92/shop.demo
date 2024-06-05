package com.shop.demo.exceptions;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {
    private String fieldName;
    private final Map<String, ?> reason;

    public ValidationException(String msg, Map<String, ?> reason) {
        super(msg);
        this.reason = reason;
    }

    public ValidationException(Map<String, ?> reason) {
        super(StringUtils.EMPTY);
        this.reason = reason;
    }

    public ValidationException(String msg) {
        super(msg);
        this.reason = null;
    }

    public ValidationException(String fieldName, String msg) {
        super(msg);
        this.fieldName = fieldName;
        this.reason = null;
    }

    public Map<String, ?> getReason() {
        return reason;
    }

    public String getFieldName() {
        return fieldName;
    }
}
