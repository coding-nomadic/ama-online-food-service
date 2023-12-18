package com.order.service.services;

import com.order.service.constants.OrderConstants;
import com.order.service.constants.OrderStatus;
import com.order.service.entities.Menu;
import com.order.service.entities.Order;
import com.order.service.exceptions.OrderServiceException;
import com.order.service.models.OrderDto;
import com.order.service.publisher.MessagePublisher;
import com.order.service.repository.MenuRepository;
import com.order.service.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class OrderService {
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final MenuRepository itemRepository;
    private final MessagePublisher messagePublisher;


    public OrderService(MessagePublisher messagePublisher, MenuRepository itemRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.messagePublisher = messagePublisher;
    }

    public Order createOrder(OrderDto orderDto) {
        validateMenuItemsExist(orderDto.getItems());
        Order order = new Order();
        order.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus(OrderStatus.ACCEPTED.name());
        order.setEmail(orderDto.getEmail());
        order.setItems(orderDto.getItems());
        Order savedOrder = orderRepository.save(order);
        messagePublisher.publishMessage(savedOrder);
        return savedOrder;
    }

    public List<Order> getAllOrders() {
        Iterable<Order> ordersIterable = orderRepository.findAll();
        return StreamSupport.stream(ordersIterable.spliterator(), false).collect(Collectors.toList());
    }

    public Order getOrderById(String id) {
        Optional<Order> order = Optional.ofNullable(orderRepository.findById(Long.valueOf(id)).orElseThrow(() -> new OrderServiceException("Order not found for : " + id, OrderConstants.ORDER_NOT_FOUND_ERROR_CODE)));
        return order.get();
    }

    private void validateMenuItemsExist(List<Menu> items) {
        for (Menu item : items) {
            if (!itemRepository.existsByName(item.getName())) {
                throw new OrderServiceException("Menu not found for : " + item.getName(), OrderConstants.MENU_NOT_FOUND_ERROR_CODE);
            }
        }
    }
}
