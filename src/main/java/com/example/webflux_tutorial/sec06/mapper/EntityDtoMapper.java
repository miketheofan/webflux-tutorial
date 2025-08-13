package com.example.webflux_tutorial.sec06.mapper;

import com.example.webflux_tutorial.sec06.dto.CustomerDto;
import com.example.webflux_tutorial.sec06.entity.Customer;

public final class EntityDtoMapper {

    private EntityDtoMapper() {}

    public static Customer toEntity(CustomerDto dto) {
        return new Customer(dto.getId(), dto.getName(), dto.getEmail());
    }

    public static CustomerDto toDto(Customer entity) {
        return new CustomerDto(entity.getId(), entity.getName(), entity.getEmail());
    }
}
