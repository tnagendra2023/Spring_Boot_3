package com.product.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


import java.sql.Date;

@Entity
@Table(name = "product_details")
@Setter
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    @Column(name = "product_name")
    private String productName;
    @Column(name = "product_price")
    private double price;
    @Column(name = "product_status")
    private String status;
    @Column(name = "product_date")
    private Date date;
}
