package com.example.webflux_tutorial.sec02.dto;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class OrderDetails {
    private UUID orderId;
    private String customerName;
    private String productName;
    private Integer amount;
    private Instant orderDate;
}
