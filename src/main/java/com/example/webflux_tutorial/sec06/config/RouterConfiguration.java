package com.example.webflux_tutorial.sec06.config;

import com.example.webflux_tutorial.sec06.exceptions.CustomerNotFoundException;
import com.example.webflux_tutorial.sec06.exceptions.InvalidInputException;
import com.example.webflux_tutorial.sec06.handlers.CustomerRequestHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
@AllArgsConstructor
public class RouterConfiguration {

    private static final String CUSTOMERS_PATH = "/customers";
    private static final String CUSTOMERS_BY_ID_PATH = "/customers/{id}";
    private static final String CUSTOMERS_PAGINATED_PATH = "/customers/paginated";

    private CustomerRequestHandler handler;
    private ApplicationExceptionHandler exceptionHandler;

    @Bean
    public RouterFunction<ServerResponse> customerRoutes() {
        return RouterFunctions.route()
                .GET(CUSTOMERS_PATH, this.handler::allCustomers)
                .GET(CUSTOMERS_PAGINATED_PATH, this.handler::paginatedCustomers)
                .GET(CUSTOMERS_BY_ID_PATH, this.handler::getCustomer)
                .POST(CUSTOMERS_PATH, this.handler::saveCustomer)
                .PUT(CUSTOMERS_BY_ID_PATH, this.handler::updateCustomer)
                .DELETE(CUSTOMERS_BY_ID_PATH, this.handler::deleteCustomer)
                .onError(CustomerNotFoundException.class, this.exceptionHandler::handleException)
                .onError(InvalidInputException.class, this.exceptionHandler::handleException)
                .build();
    }
}
