package com.shop.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestClientException;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundException extends RestClientException {
    protected String fieldName;

    public NotFoundException(String msg) {
        super(msg);
    }

    public NotFoundException(String fieldName, String msg) {
        super(msg);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
