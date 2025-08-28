package com.example.webflux_tutorial.sec06.handlers;

import com.example.webflux_tutorial.sec06.enums.Operation;
import com.example.webflux_tutorial.sec06.service.CalculatorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@AllArgsConstructor
public class CalculatorRequestHandler {

    private final CalculatorService service;

    public Mono<ServerResponse> calculate(ServerRequest request) {
        try {
            int a = Integer.parseInt(request.pathVariable("a"));
            int b = Integer.parseInt(request.pathVariable("b"));
            String operationSymbol = request.headers().firstHeader("operation");

            Operation operation = Operation.fromSymbol(operationSymbol)
                    .orElseThrow(() -> new IllegalArgumentException("operation header should be + - * /"));

            return service.calculate(a, b, operation)
                    .map(result -> Map.of(
                            "result", result
                    ))
                    .flatMap(response -> ServerResponse.ok().bodyValue(response));

        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().bodyValue("Invalid number format");
        } catch (IllegalArgumentException e) {
            return ServerResponse.badRequest().bodyValue(e.getMessage());
        }
    }
}
