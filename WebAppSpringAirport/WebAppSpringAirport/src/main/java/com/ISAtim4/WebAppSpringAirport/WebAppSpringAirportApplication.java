package com.ISAtim4.WebAppSpringAirport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = { "com.ISAtim4.WebAppSpringAirport" }, exclude = { SecurityAutoConfiguration.class })
@EnableTransactionManagement
@EnableJpaRepositories
public class WebAppSpringAirportApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppSpringAirportApplication.class, args);
	}

}
