package com.example.webflux_tutorial;

import com.example.webflux_tutorial.properties.ExternalServicesProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = "com.example.${sec}")
@EnableConfigurationProperties(ExternalServicesProperties.class)
@EnableR2dbcRepositories
public class WebfluxTutorialApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxTutorialApplication.class, args);
	}

}
