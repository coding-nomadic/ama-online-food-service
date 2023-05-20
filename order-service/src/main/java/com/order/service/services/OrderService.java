package com.order.service.services;

import com.order.service.entities.Order;
import com.order.service.exceptions.OrderServiceException;
import com.order.service.models.OrderDto;
import com.order.service.models.OrderDtoResponse;
import com.order.service.publisher.MessagePublisher;
import com.order.service.repository.ItemRepository;
import com.order.service.repository.OrderRepository;
import com.order.service.utils.GenericUtils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class OrderService {


    private ModelMapper modelMapper;
    private OrderRepository orderRepository;

    private ItemRepository itemRepository;

    private MessagePublisher messagePublisher;

    public OrderService(MessagePublisher messagePublisher, ItemRepository itemRepository, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
        this.messagePublisher = messagePublisher;
    }

    public OrderDtoResponse createOrders(OrderDto orderDto) {
        orderDto.getItems().forEach(s -> {
            if (!itemRepository.existsByName(s.getName())) {
                throw new OrderServiceException("Item not found for " + s.getName(), "102");
            }
        });
        Order order = orderRepository.save(modelMapper.map(orderDto, Order.class));
        OrderDtoResponse orderDtoResponse = GenericUtils.prepareOrderResponse(order, modelMapper);
        messagePublisher.publishMessage(orderDtoResponse);
        return orderDtoResponse;
    }
}
