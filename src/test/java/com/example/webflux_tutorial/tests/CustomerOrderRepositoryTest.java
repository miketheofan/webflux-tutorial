package com.example.webflux_tutorial.tests;

import com.example.webflux_tutorial.sec02.repository.CustomerOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@Slf4j
public class CustomerOrderRepositoryTest extends AbstractTest {

    @Autowired
    private CustomerOrderRepository repository;

    @Test
    public void productsOrderedByCustomer() {
        this.repository.getProductsOrderedByCustomer("mike")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void orderDetailsByProduct() {
        this.repository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(dto -> Assertions.assertEquals(975, dto.getAmount()))
                .assertNext(dto -> Assertions.assertEquals(950, dto.getAmount()))
                .expectComplete()
                .verify();
    }
}
