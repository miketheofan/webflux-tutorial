package com.example.webflux_tutorial.tests.sec02;

import com.example.webflux_tutorial.sec02.dto.OrderDetails;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import reactor.test.StepVerifier;

@Slf4j
class DataBaseClientTest extends AbstractTest {

    @Autowired
    private DatabaseClient client;

    @Test
    void orderDetailsByProduct() {
        String query = """
            SELECT
                co.order_id,
                c.name AS customer_name,
                p.description AS product_name,
                co.amount,
                co.order_date
            FROM
                customer c
            INNER JOIN customer_order co ON c.id = co.customer_id
            INNER JOIN product p ON p.id = co.product_id
            WHERE
                p.description = :description
            ORDER BY co.amount DESC
            """;

        this.client.sql(query)
                .bind("description", "iphone 20")
                .mapProperties(OrderDetails.class)
                .all()
                .doOnNext(c -> log.info("{}", c))
                .as(StepVerifier::create)
                .assertNext(dto -> Assertions.assertEquals(975, dto.getAmount()))
                .assertNext(dto -> Assertions.assertEquals(950, dto.getAmount()))
                .expectComplete()
                .verify();
    }
}
