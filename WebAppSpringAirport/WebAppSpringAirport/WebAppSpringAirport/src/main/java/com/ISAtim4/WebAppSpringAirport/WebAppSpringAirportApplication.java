package com.ISAtim4.WebAppSpringAirport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.ISAtim4.WebAppSpringAirport.security.auth"},exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories("com.ISAtim4.WebAppSpringAirport.security.auth")
public class WebAppSpringAirportApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppSpringAirportApplication.class, args);
	}

}
