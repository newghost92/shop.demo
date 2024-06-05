package com.shop.demo.controller;

import com.shop.demo.service.CustomerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(
        CustomerService customerService
    ) {
        this.customerService = customerService;
    }
}
