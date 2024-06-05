package com.shop.demo.service;

import com.shop.demo.domain.dto.request.CreateOrderRequest;
import com.shop.demo.domain.dto.response.PageResponse;
import com.shop.demo.domain.dto.request.SearchOrderRequest;
import com.shop.demo.domain.dto.response.OrderDetail;

public interface OrderService {

    void createOrder(CreateOrderRequest request);

    OrderDetail getDetail(Long id);

    void cancelOrder(Long id);

    PageResponse<OrderDetail> search(SearchOrderRequest request);
}
