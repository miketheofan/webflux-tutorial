package com.example.webflux_tutorial.tests;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "sec=sec02",
//        "logging.level.org.spring.framework.r2dbc=DEBUG"
})
public abstract class AbstractTest {
}
