package com.example.webflux_tutorial.sec01.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external")
@Data
public class ExternalServicesProperties {

    private Demo01 demo01;

    @Data
    public static class Demo01 {
        private String productsUri;
        private String productsNotoriousUri;
    }
}
