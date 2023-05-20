package com.order.service.models;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Valid
public class ItemDto implements Serializable {

    @Valid
    @NotEmpty(message = "Name is mandatory")
    private String name;
    @Valid
    @NotEmpty(message = "Quantity is mandatory")
    private String quantity;
    @Valid
    @NotEmpty(message = "Price is mandatory")
    private Long price;
}
