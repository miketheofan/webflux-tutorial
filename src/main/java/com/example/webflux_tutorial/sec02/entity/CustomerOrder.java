package com.example.webflux_tutorial.sec02.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("customer_order")
@Data
public class CustomerOrder {
    @Column("orderId")
    @Id
    private UUID orderId;
    @Column("customerId")
    private Integer customerId;
    @Column("productId")
    private Integer productId;
    @Column("amount")
    private Integer amount;
    @Column("orderDate")
    private Instant orderDate;
}
