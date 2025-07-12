package com.example.webflux_tutorial.sec02.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("customer")
@Data
public class Customer {
    @Column("id")
    @Id
    private Integer id;
    @Column("name")
    private String name;
    @Column("email")
    private String email;
}
