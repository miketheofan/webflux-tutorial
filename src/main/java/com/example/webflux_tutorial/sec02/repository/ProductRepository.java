package com.example.webflux_tutorial.sec02.repository;

import com.example.webflux_tutorial.sec02.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {
    Flux<Product> findByPriceBetween(int from, int to);
    Flux<Product> findAllBy(Pageable pageable);
}
