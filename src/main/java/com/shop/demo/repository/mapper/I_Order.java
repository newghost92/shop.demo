package com.shop.demo.repository.mapper;

import java.math.BigDecimal;

public interface I_Order {

    Long getId();

    Long getCustomerId();

    String getCustomerName();

    String getCustomerAddress();

    String getCustomerEmail();

    String getCustomerTel();

    BigDecimal getTotalPrice();

    Integer getStatus();
}
