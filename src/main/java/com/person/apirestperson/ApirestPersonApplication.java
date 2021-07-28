package com.person.apirestperson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@SpringBootApplication(scanBasePackages = "com.person.apirestperson")
//@EntityScan("com.person.apirestperson")
//@EnableJpaRepositories("com.person.apirestperson")
@SpringBootApplication
public class ApirestPersonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApirestPersonApplication.class, args);
	}
}
