package com.order.service.utils;

import com.order.service.entities.Order;
import com.order.service.models.ItemDtoResponse;
import com.order.service.models.OrderDtoResponse;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GenericUtils {

    private  GenericUtils(){

    }

    /** Order Response **/
    public static OrderDtoResponse prepareOrderResponse(Order order, ModelMapper modelMapper) {
        OrderDtoResponse orderDtoResponse = new OrderDtoResponse();
        orderDtoResponse.setCreatedAt(order.getCreatedAt());
        orderDtoResponse.setId(order.getId());
        orderDtoResponse.setEmail(order.getEmail());
        orderDtoResponse.setStatus(order.getStatus());
        List<ItemDtoResponse> items = order.getItems().stream().map(m -> modelMapper.map(m, ItemDtoResponse.class)).collect(Collectors.toList());
        orderDtoResponse.setItems(items);
        return orderDtoResponse;
    }
}
