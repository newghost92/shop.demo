package com.shop.demo.domain.dto.response;

import com.shop.demo.domain.entities.Order;
import com.shop.demo.enums.OrderStatus;
import com.shop.demo.repository.mapper.I_Order;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.i18n.LocaleContextHolder;

public class OrderDetail {

    private Long id;
    private CustomerDetail customer;
    private BigDecimal totalPrice;
    private String status;
    private Set<OrderItemDetail> items;

    public OrderDetail(Order order) {
        this.id = order.getId();
        this.customer = new CustomerDetail(order.getCustomer());
        this.totalPrice = order.getTotalPrice();
        this.status = order.getStatus().getName(LocaleContextHolder.getLocale().getLanguage());
        this.items = order.getOrderItems().stream().map(OrderItemDetail::new).collect(Collectors.toSet());
    }

    public OrderDetail(I_Order io) {
        this.id = io.getId();
        this.customer = new CustomerDetail(io);
        this.totalPrice = io.getTotalPrice();
        this.status = OrderStatus.get(io.getStatus()).getName(LocaleContextHolder.getLocale().getLanguage());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CustomerDetail getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerDetail customer) {
        this.customer = customer;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<OrderItemDetail> getItems() {
        return items;
    }

    public void setItems(Set<OrderItemDetail> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id=" + id +
                ", customer=" + customer +
                ", totalPrice=" + totalPrice +
                ", status='" + status + '\'' +
                ", items=" + items +
                '}';
    }
}
