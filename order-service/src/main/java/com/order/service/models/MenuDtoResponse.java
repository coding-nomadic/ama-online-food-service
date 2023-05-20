package com.order.service.models;


import lombok.Data;

import java.io.Serializable;

@Data
public class MenuDtoResponse implements Serializable {

    private Long id;
    private String name;
    private String quantity;
    private Long price;
}
