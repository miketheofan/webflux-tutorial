package com.example.webflux_tutorial.tests;

import com.example.webflux_tutorial.sec02.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

@Slf4j
public class ProductRepositoryTest extends AbstractTest {

    @Autowired
    private ProductRepository repository;

    @Test
    public void findProductsInPriceRange() {
        this.repository.findByPriceBetween(500, 1500)
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals("iphone 20", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("iphone 18", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("ipad", p.getDescription()))
                .assertNext(p -> Assertions.assertEquals("macbook air", p.getDescription()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findAllPageable() {
        this.repository.findAllBy(PageRequest.of(0, 3).withSort(Sort.by("price").ascending()))
                .doOnNext(p -> log.info("{}", p))
                .as(StepVerifier::create)
                .assertNext(p -> Assertions.assertEquals(200, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(250, p.getPrice()))
                .assertNext(p -> Assertions.assertEquals(300, p.getPrice()))
                .expectComplete()
                .verify();
    }
}
