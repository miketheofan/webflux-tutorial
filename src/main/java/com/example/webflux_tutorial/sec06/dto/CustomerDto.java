package com.example.webflux_tutorial.sec06.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private String name;
    private String email;
}
