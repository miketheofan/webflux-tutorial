package com.example.webflux_tutorial.sec06.service;

import com.example.webflux_tutorial.sec06.enums.Operation;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CalculatorService {
    public Mono<Integer> calculate(int a, int b, Operation operation) {
        return Mono.fromSupplier(() -> operation.calculate(a, b));
    }
}
