package com.example.webflux_tutorial.controller;

import com.example.webflux_tutorial.model.Product;
import com.example.webflux_tutorial.properties.ExternalServicesProperties;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("reactive")
@AllArgsConstructor
//@Slf4j
public class ReactiveWebController {

    private static final Logger log = LoggerFactory.getLogger(ReactiveWebController.class);
    private final WebClient webClient;
    private final ExternalServicesProperties properties;

    @GetMapping("/products")
    public Flux<Product> getProducts() {
        return this.webClient.get()
                .uri(properties.getDemo01().getProductsUri())
                .retrieve()
                .bodyToFlux(Product.class)
                .doOnNext(p -> log.info("Received product: {}", p));
    }

    @GetMapping(value = "/products/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> getProductsStream() {
        return this.webClient.get()
                .uri(properties.getDemo01().getProductsUri())
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(p -> log.info("Received product: {}", p));

    }

    @GetMapping("/products/notorious")
    public Flux<Product> getProductsNotorious() {
        return this.webClient.get()
                .uri(properties.getDemo01().getProductsNotoriousUri())
                .retrieve()
                .bodyToFlux(Product.class)
                .onErrorComplete()
                .doOnNext(p -> log.info("Received product: {}", p));
    }
}
