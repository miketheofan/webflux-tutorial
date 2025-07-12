package com.example.webflux_tutorial.sec02.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("product")
@Data
public class Product {
    @Column("id")
    private Integer id;
    @Column("description")
    private String description;
    @Column("price")
    private Integer price;
}
