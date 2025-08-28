package com.example.webflux_tutorial.sec06.config;

import com.example.webflux_tutorial.sec06.enums.Operation;
import com.example.webflux_tutorial.sec06.handlers.CalculatorRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Optional;

@Configuration
@AllArgsConstructor
public class CalculatorConfiguration {

    private final CalculatorRequestHandler handler;

    @Bean
    public RouterFunction<ServerResponse> calculatorRoutes() {
        return RouterFunctions.route()
                .GET("/calculator/{a}/{b}", handler::calculate)
                .filter((request, next) ->
                        Optional.ofNullable(request.headers().firstHeader("operation"))
                                .flatMap(Operation::fromSymbol)
                                .map(op -> next.handle(request))
                                .orElse(ServerResponse.badRequest()
                                        .bodyValue("operation header should be + - * /"))
                )
                .build();
    }
}
