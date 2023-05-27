package com.restaurant.service.services;

import com.restaurant.service.entities.Order;
import com.restaurant.service.models.OrderDtoResponse;
import com.restaurant.service.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RestaurantService {

    private OrderRepository orderRepository;
    private ModelMapper modelMapper;

    private static final String DELIVERED = "delivered";

    public RestaurantService(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    /**
     * update order details
     **/
    public void updateOrderDetails(OrderDtoResponse orderDtoResponse) {
        final Order order = modelMapper.map(orderDtoResponse, Order.class);
        order.setStatus(DELIVERED);
        orderRepository.save(order);
        log.info("Updated Order Details with status as Delivered");
    }
}
