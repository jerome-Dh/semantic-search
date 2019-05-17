package com.example.pgsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PgsqlApplication {

	public static void main(String[] args) {
		SpringApplication.run(PgsqlApplication.class, args);
	}

}
