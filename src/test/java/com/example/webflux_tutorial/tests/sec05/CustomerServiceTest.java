package com.example.webflux_tutorial.tests.sec05;

import com.example.webflux_tutorial.sec05.dto.CustomerDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Objects;

@SpringBootTest(properties = "sec=sec05")
@AutoConfigureWebTestClient
@Slf4j
class CustomerServiceTest {

    @Autowired
    private WebTestClient client;

    @Test
    void allCustomers() {
        this.client.get()
                .uri("/customers")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDto.class)
                .value(list -> log.info("{}", list))
                .hasSize(10);
    }

    @Test
    void paginatedCustomers() {
        this.client.get()
                .uri("/customers/paginated?page=3&size=2")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(r.getResponseBody())))
                .jsonPath("$.length()").isEqualTo(2)
                .jsonPath("$[0].id").isEqualTo(5)
                .jsonPath("$[1].id").isEqualTo(6);
    }

    @Test
    void customerById() {
        this.client.get()
                .uri("/customers/1")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(1)
                .jsonPath("$.name").isEqualTo("sam")
                .jsonPath("$.email").isEqualTo("sam@gmail.com");
    }

    @Test
    void createAndDeleteCustomer() {
        // create
        var dto = CustomerDto.builder()
                .id(null)
                .name("marshal")
                .email("marshal@gmail.com")
                .build();

        this.client.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(11)
                .jsonPath("$.name").isEqualTo("marshal")
                .jsonPath("$.email").isEqualTo("marshal@gmail.com");

        // delete
        this.client.delete()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody().isEmpty();
    }

    @Test
    void updateCustomer() {
        var dto = CustomerDto.builder()
                .id(null)
                .name("noel")
                .email("noel@gmail.com")
                .build();

        this.client.put()
                .uri("/customers/10")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody()
                .consumeWith(r -> log.info("{}", new String(Objects.requireNonNull(r.getResponseBody()))))
                .jsonPath("$.id").isEqualTo(10)
                .jsonPath("$.name").isEqualTo("noel")
                .jsonPath("$.email").isEqualTo("noel@gmail.com");
    }

    @Test
    void customerNotFound() {
        // get
        this.client.get()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Customer not found");

        // delete
        this.client.delete()
                .uri("/customers/11")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.title").isEqualTo("Customer not found");

        // put
        var dto = CustomerDto.builder()
                .id(null)
                .name("noel")
                .email("noel@gmail.com")
                .build();

        this.client.put()
                .uri("/customers/11")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Customer [id=15] not found");
    }

    @Test
    void invalidInput() {
        var missingEmail = CustomerDto.builder()
                .id(null)
                .name("albert")
                .email(null)
                .build();

        this.client.post()
                .uri("/customers")
                .bodyValue(missingEmail)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Valid email is required");

        var invalidEmail = CustomerDto.builder()
                .id(null)
                .name("albert")
                .email("albertgmail.com")
                .build();

        this.client.post()
                .uri("/customers")
                .bodyValue(invalidEmail)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Valid email is required");

        var missingName = CustomerDto.builder()
                .id(null)
                .name(null)
                .email("albert@gmail.com")
                .build();

        this.client.post()
                .uri("/customers")
                .bodyValue(missingName)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$.detail").isEqualTo("Name is required");
    }

    @Test
    void unauthorized() {
        // no token
        this.client.get()
                .uri("/customers")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNAUTHORIZED);

        // invalid token
        this.validateGet("secret", HttpStatus.UNAUTHORIZED);
    }

    @Test
    void standardCategory() {
        var key = "secret123";

        this.validateGet(key, HttpStatus.OK);
        this.validatePost(key, HttpStatus.FORBIDDEN);
    }

    @Test
    void adminCategory() {
        var key = "secret456";

        this.validateGet(key, HttpStatus.OK);
        this.validatePost(key, HttpStatus.OK);
    }

    private void validateGet(String token, HttpStatus expectedStatus) {
        this.client.get()
                .uri("/customers")
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }

    private void validatePost(String token, HttpStatus expectedStatus) {
        var dto = CustomerDto.builder()
                .id(null)
                .name("marshal")
                .email("marshal@gmail.com")
                .build();

        this.client.post()
                .uri("/customers")
                .bodyValue(dto)
                .header("auth-token", token)
                .exchange()
                .expectStatus().isEqualTo(expectedStatus);
    }
}