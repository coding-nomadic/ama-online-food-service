package com.order.service.utils;

import com.order.service.entities.Order;
import com.order.service.models.ItemDtoResponse;
import com.order.service.models.OrderDtoResponse;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class GenericUtils {

    private GenericUtils() {

    }

    /**
     * Order Response
     **/
    public static OrderDtoResponse orderResponse(Order order, ModelMapper modelMapper) {
        List<ItemDtoResponse> items = order.getItems().stream().map(m -> modelMapper.map(m, ItemDtoResponse.class)).collect(Collectors.toList());
        return OrderDtoResponse.builder().id(order.getId()).email(order.getEmail()).status(order.getStatus()).createdAt(order.getCreatedAt()).items(items).build();
    }
}
