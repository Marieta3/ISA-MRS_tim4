package com.ISAtim4.WebAppSpringAirport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/*
 * Milan: 404 vam je javljao jer ste ogranicili skeniranje paketa na samo paket security pa nisu registrovani kontroleri
 * Sada vam skenira celu aplikaciju jer je postavljen koreni paket i sve komponente mozete koristiti
 */
@SpringBootApplication(scanBasePackages={"com.ISAtim4.WebAppSpringAirport"},exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
public class WebAppSpringAirportApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppSpringAirportApplication.class, args);
	}

}
