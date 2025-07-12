package com.example.webflux_tutorial;

import com.example.webflux_tutorial.sec01.properties.ExternalServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@SpringBootApplication(scanBasePackages = "com.example.webflux_tutorial.${sec}")
@EnableConfigurationProperties(ExternalServicesProperties.class)
@EnableR2dbcRepositories(basePackages = "com.example.webflux_tutorial.${sec}")
public class WebfluxTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxTutorialApplication.class, args);
	}

}
