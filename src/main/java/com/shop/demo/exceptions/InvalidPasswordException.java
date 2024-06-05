package com.shop.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends BadRequestException {

    public InvalidPasswordException(String msg) {
        super(msg);
    }
    public InvalidPasswordException(String fieldName, String msg) {
        super(msg);
        this.fieldName = fieldName;
    }
}
