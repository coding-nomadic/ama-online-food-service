package com.order.service.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tbl_menu")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "quantity", nullable = false)
    private String quantity;

    @Column(name = "price", nullable = false)
    private Long price;
}
