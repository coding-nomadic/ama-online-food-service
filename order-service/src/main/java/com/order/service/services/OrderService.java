package com.order.service.services;

import com.order.service.entities.Order;
import com.order.service.exceptions.OrderServiceException;
import com.order.service.models.OrderDto;
import com.order.service.models.OrderDtoResponse;
import com.order.service.publisher.MessagePublisher;
import com.order.service.repository.MenuRepository;
import com.order.service.repository.OrderRepository;
import com.order.service.utils.GenericUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {


    private ModelMapper modelMapper;
    private OrderRepository orderRepository;

    private MenuRepository itemRepository;

    private MessagePublisher messagePublisher;

    @Autowired
    public OrderService(MessagePublisher messagePublisher, MenuRepository itemRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.messagePublisher = messagePublisher;
    }

    public OrderDtoResponse createOrders(OrderDto orderDto) {
        orderDto.getItems().forEach(s -> {
            if (!itemRepository.existsByName(s.getName())) {
                throw new OrderServiceException("Menu not found for " + s.getName(), "102");
            }
        });
        Order order = new Order();
        order.setCreatedAt(new java.sql.Date(System.currentTimeMillis()));
        order.setStatus(orderDto.getStatus());
        order.setEmail(orderDto.getEmail());
        order.setItems(orderDto.getItems());
        OrderDtoResponse orderDtoResponse = GenericUtils.orderResponse(orderRepository.save(order), modelMapper);
        messagePublisher.publishMessage(orderDtoResponse);
        return orderDtoResponse;
    }

    public List<OrderDtoResponse> getAllOrders() {
        return orderRepository.findAll().stream().map(m -> modelMapper.map(m, OrderDtoResponse.class)).collect(Collectors.toList());
    }


    public OrderDtoResponse getOrderById(String id) {
        Optional<Order> order = orderRepository.findById(Long.valueOf(id));
        if (order.isPresent()) {
            return modelMapper.map(order.get(), OrderDtoResponse.class);
        } else {
            throw new OrderServiceException("Order not found for " + id, "102");
        }
    }
}
