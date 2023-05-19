package com.restaurant.service.entities;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
@Entity
@Table(name = "tbl_order")
@Data
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "createdAt", nullable = false)
    private Date createdAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Item> items;
}
