package com.shop.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends NotFoundException {

    public UserNotFoundException(String msg) {
        super(msg);
    }
    public UserNotFoundException() {
        super("User not found");
    }
}
