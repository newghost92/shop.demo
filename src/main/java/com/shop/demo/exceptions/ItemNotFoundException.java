package com.shop.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Item not found!")
public class ItemNotFoundException extends NotFoundException {

    public ItemNotFoundException(String name) {
        super("Item " + name + " not found!");
    }
    public ItemNotFoundException() {
        super("Item not found!");
    }
}
