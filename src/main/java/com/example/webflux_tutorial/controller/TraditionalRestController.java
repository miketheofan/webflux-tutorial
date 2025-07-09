package com.example.webflux_tutorial.controller;

import com.example.webflux_tutorial.properties.ExternalServicesProperties;
import com.example.webflux_tutorial.model.Product;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("/traditional")
@AllArgsConstructor
//@Slf4j
public class TraditionalRestController {

    private static final Logger log = LoggerFactory.getLogger(TraditionalRestController.class);

    private final RestClient restClient;
    private final ExternalServicesProperties properties;

    @GetMapping("/products")
    public List<Product> getProducts() {

        List<Product> list = this.restClient.get()
                .uri(properties.getDemo01())
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {
                });
        log.info("Received response: {}", list);

        return list;
    }
}
