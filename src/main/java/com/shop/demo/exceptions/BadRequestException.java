package com.shop.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RestClientException {
    protected String fieldName;
    public BadRequestException(String msg) {
        super(msg);
    }

    public BadRequestException(String fieldName, String msg) {
        super(msg);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
