package com.shop.demo.service.impl;

import com.shop.demo.domain.entities.Order;
import com.shop.demo.utils.BigDecimalUtil;
import com.shop.demo.domain.entities.Item;
import com.shop.demo.domain.entities.OrderItem;
import com.shop.demo.repository.OrderItemRepository;
import com.shop.demo.service.OrderItemService;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(
        OrderItemRepository orderItemRepository
    ) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void create(Order order, Map<Item, Integer> cart) {
        if (order == null || cart == null) return;
        Set<OrderItem> set = new HashSet<>();
        for (Map.Entry<Item, Integer> e : cart.entrySet()) {
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setItem(e.getKey());
            oi.setItemNumber(e.getValue());
            BigDecimal totalPrice = BigDecimalUtil.roundUp(e.getKey().getPrice().multiply(BigDecimal.valueOf(e.getValue())));
            oi.setTotalPrice(totalPrice);
            set.add(oi);
        }
        orderItemRepository.saveAll(set);
    }
}
