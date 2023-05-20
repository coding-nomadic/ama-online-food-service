package com.order.service.controllers;


import com.order.service.models.OrderDto;
import com.order.service.models.OrderDtoResponse;
import com.order.service.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/v1/orders")
@Validated
public class OrderController {


    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDtoResponse> createItems(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrders(orderDto));
    }
}
