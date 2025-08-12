package com.example.webflux_tutorial.sec05.mapper;

import com.example.webflux_tutorial.sec05.dto.CustomerDto;
import com.example.webflux_tutorial.sec05.entity.Customer;

public final class EntityDtoMapper {

    private EntityDtoMapper() {}

    public static Customer toEntity(CustomerDto dto) {
        return new Customer(dto.getId(), dto.getName(), dto.getEmail());
    }

    public static CustomerDto toDto(Customer entity) {
        return CustomerDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }
}
