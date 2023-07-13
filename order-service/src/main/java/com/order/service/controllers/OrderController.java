package com.order.service.controllers;


import com.order.service.models.OrderDto;
import com.order.service.models.OrderDtoResponse;
import com.order.service.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/orders")
@Validated
public class OrderController {


    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDtoResponse> createOrders(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrders(orderDto));
    }

    @GetMapping
    public ResponseEntity<List<OrderDtoResponse>> fetchAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<OrderDtoResponse> fetchById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
