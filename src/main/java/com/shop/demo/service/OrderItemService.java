package com.shop.demo.service;

import com.shop.demo.domain.entities.Order;
import com.shop.demo.domain.entities.Item;
import java.util.Map;

public interface OrderItemService {

    void create(Order order, Map<Item, Integer> cart);
}
