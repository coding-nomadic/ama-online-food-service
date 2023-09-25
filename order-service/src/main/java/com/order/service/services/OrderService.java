package com.order.service.services;

import com.order.service.entities.Order;
import com.order.service.exceptions.OrderServiceException;
import com.order.service.models.OrderDto;
import com.order.service.models.OrderDtoResponse;
import com.order.service.models.OrderStatus;
import com.order.service.publisher.MessagePublisher;
import com.order.service.repository.MenuRepository;
import com.order.service.repository.OrderRepository;
import com.order.service.utils.GenericUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public OrderDtoResponse createOrder(OrderDto orderDto) {
        validateMenuItemsExist(orderDto.getItems());

        Order order = new Order();
        order.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus(OrderStatus.ACCEPTED.name());
        order.setEmail(orderDto.getEmail());
        order.setItems(orderDto.getItems());

        Order savedOrder = orderRepository.save(order);
        OrderDtoResponse orderDtoResponse = GenericUtils.orderResponse(savedOrder, modelMapper);
        messagePublisher.publishMessage(orderDtoResponse);

        return orderDtoResponse;
    }

    public List<OrderDtoResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDtoResponse.class))
                .collect(Collectors.toList());
    }

    public OrderDtoResponse getOrderById(String id) {
        Optional<Order> order = orderRepository.findById(Long.valueOf(id));
        return order.map(value -> modelMapper.map(value, OrderDtoResponse.class))
                .orElseThrow(() -> new OrderServiceException("Order not found for " + id, "102"));
    }

    private void validateMenuItemsExist(List<OrderItem> items) {
        for (OrderItem item : items) {
            if (!itemRepository.existsByName(item.getName())) {
                throw new OrderServiceException("Menu not found for " + item.getName(), "102");
            }
        }
    }
}
