package com.example.webflux_tutorial.sec06.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public enum Operation {

    ADD("+", Integer::sum),
    SUBTRACT("-", (a, b) -> a - b),
    MULTIPLY("*", (a, b) -> a * b),
    DIVIDE("/", (a, b) -> a / b
    );

    private final String symbol;
    private final BinaryOperator<Integer> calculator;

    public int calculate(int a, int b) {
        return calculator.apply(a, b);
    }

    private static final Map<String, Operation> LOOKUP =
            Arrays.stream(values()).collect(Collectors.toMap(Operation::getSymbol, Function.identity()));

    public static Optional<Operation> fromSymbol(String symbol) {
        return Optional.ofNullable(LOOKUP.get(symbol));
    }
}
