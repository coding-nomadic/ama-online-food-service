package com.order.service.controllers;


import com.order.service.entities.Order;
import com.order.service.models.OrderDto;
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
    public ResponseEntity<Order> createOrders(@Valid @RequestBody OrderDto orderDto) {
        return ResponseEntity.ok(orderService.createOrder(orderDto));
    }

    @GetMapping
    public ResponseEntity<List<Order>> fetchAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Order> fetchById(@PathVariable String id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
