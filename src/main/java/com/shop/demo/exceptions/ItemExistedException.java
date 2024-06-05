package com.shop.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Item already exists!")
public class ItemExistedException extends IllegalArgumentException {

    public ItemExistedException(String name) {
        super("Item " + name + " already exists!");
    }

    public ItemExistedException() {
        super("Item already exists!");
    }
}
