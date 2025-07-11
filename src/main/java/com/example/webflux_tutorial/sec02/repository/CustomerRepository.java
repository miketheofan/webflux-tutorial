package com.example.webflux_tutorial.sec02.repository;

import com.example.webflux_tutorial.sec02.entity.Customer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    Flux<Customer> findByName(String name);
    Flux<Customer> findByEmailEndingWith(String email);
}
