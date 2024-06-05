package com.shop.demo.controller;

import com.shop.demo.domain.dto.request.CreateOrderRequest;
import com.shop.demo.domain.dto.response.WrapRestResponse;
import com.shop.demo.domain.dto.request.SearchOrderRequest;
import com.shop.demo.service.OrderService;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
        OrderService orderService
    ) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<WrapRestResponse> create(@Valid @ModelAttribute CreateOrderRequest request) {
        orderService.createOrder(request);
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WrapRestResponse> getDetail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(new WrapRestResponse(orderService.getDetail(id)));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<WrapRestResponse> cancel(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
        return ResponseEntity.ok(new WrapRestResponse());
    }

    @PostMapping("/search")
    public ResponseEntity<WrapRestResponse> search(@Valid @RequestBody SearchOrderRequest request) {
        return ResponseEntity.ok(new WrapRestResponse(orderService.search(request)));
    }
}
