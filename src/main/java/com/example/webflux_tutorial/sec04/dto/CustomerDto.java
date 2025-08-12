package com.example.webflux_tutorial.sec04.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CustomerDto {
    private Integer id;
    private String name;
    private String email;
}
