package com.restaurant.service.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemDtoResponse implements Serializable {
    private Long id;
    private String name;
    private String quantity;
    private Long price;
}