package com.shop.demo.domain.dto.response;

import com.shop.demo.domain.entities.OrderItem;
import java.math.BigDecimal;

public class OrderItemDetail {

    private Long id;
    private Long orderId;
    private Long itemId;
    private Integer itemNumber;
    private BigDecimal totalPrice;

    public OrderItemDetail(OrderItem oi) {
        this.id = oi.getId();
        this.orderId = oi.getOrder().getId();
        this.itemId = oi.getItem().getId();
        this.itemNumber = oi.getItemNumber();
        this.totalPrice = oi.getTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Integer getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Integer itemNumber) {
        this.itemNumber = itemNumber;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderItemDetail{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", itemId=" + itemId +
                ", itemNumber=" + itemNumber +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
