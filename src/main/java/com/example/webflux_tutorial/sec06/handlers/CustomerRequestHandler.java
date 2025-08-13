package com.example.webflux_tutorial.sec06.handlers;

import com.example.webflux_tutorial.sec06.dto.CustomerDto;
import com.example.webflux_tutorial.sec06.exceptions.ApplicationExceptions;
import com.example.webflux_tutorial.sec06.service.CustomerService;
import com.example.webflux_tutorial.sec06.validator.RequestValidator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class CustomerRequestHandler {

    private CustomerService service;

    public Mono<ServerResponse> allCustomers(ServerRequest request) {
        return this.service.getAllCustomers()
                .as(flux -> ServerResponse.ok().body(flux, CustomerDto.class));
    }

    public Mono<ServerResponse> paginatedCustomers(ServerRequest request) {
        var page = request.queryParam("page").map(Integer::parseInt).orElse(1);
        var size = request.queryParam("size").map(Integer::parseInt).orElse(3);

        return this.service.getAllCustomers(page, size)
                .collectList()
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> getCustomer(ServerRequest request) {
        final var id = Integer.parseInt(request.pathVariable("id"));

//        return this.service.getCustomerById(id)
//                .as(flux -> ServerResponse.ok().body(flux, CustomerDto.class));
        return this.service.getCustomerById(id)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> saveCustomer(ServerRequest request) {
        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(this.service::saveCustomer)
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> updateCustomer(ServerRequest request) {
        final var id = Integer.parseInt(request.pathVariable("id"));

        return request.bodyToMono(CustomerDto.class)
                .transform(RequestValidator.validate())
                .as(validatedReq -> this.service.updateCustomer(id, validatedReq))
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .flatMap(ServerResponse.ok()::bodyValue);
    }

    public Mono<ServerResponse> deleteCustomer(ServerRequest request) {
        final var id = Integer.parseInt(request.pathVariable("id"));

        return this.service.deleteCustomerById(id)
                .filter(b -> b)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id))
                .then(ServerResponse.ok().build());
    }
}
