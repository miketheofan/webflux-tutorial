package com.example.webflux_tutorial.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "external")
@Data
public class ExternalServicesProperties {
    private String demo01;
}
