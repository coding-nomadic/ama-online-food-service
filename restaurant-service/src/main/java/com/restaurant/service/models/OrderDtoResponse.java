package com.restaurant.service.models;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class OrderDtoResponse implements Serializable {
    private Long id;
    private String status;
    private String email;
    private Date createdAt;
    private List<ItemDtoResponse> items;
}