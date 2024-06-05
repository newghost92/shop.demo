package com.shop.demo.exceptions;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CombinedValidationException extends RuntimeException {
    private final Map<String, ?> reason;

    public CombinedValidationException(String msg, Map<String, ?> reason) {
        super(msg);
        this.reason = reason;
    }

    public CombinedValidationException(Map<String, ?> reason) {
        super(StringUtils.EMPTY);
        this.reason = reason;
    }

    public Map<String, ?> getReason() {
        return reason;
    }
}
