package com.order.service.models;


import com.order.service.entities.Menu;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Validated
public class OrderDto implements Serializable {
    @NotBlank(message = "Status is mandatory")
    private String status;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @NotBlank(message = "Email is mandatory")
    private String email;
    private Date createdAt;
    private List<Menu> items;
}
