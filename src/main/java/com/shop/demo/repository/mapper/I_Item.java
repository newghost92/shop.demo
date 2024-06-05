package com.shop.demo.repository.mapper;

import java.math.BigDecimal;

public interface I_Item {

    Long getId();

    String getName();

    String getDescription();

    BigDecimal getPrice();

    Integer getStatus();

    Long getInventory();
}
