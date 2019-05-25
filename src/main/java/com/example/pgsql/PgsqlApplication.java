package com.example.pgsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.example.pgsql.repository.UserRepository;
import com.example.pgsql.model.Users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableJpaAuditing
public class PgsqlApplication {

	private static final Logger log = LoggerFactory.getLogger(PgsqlApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(PgsqlApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository repository) {
		return (args) -> {
			// save a couple of customers
			repository.save(new Users("Jack", "Bauer", "Jack", "bhgfh", "ghgh", "f"));
			repository.save(new Users("Jack2", "Bauer", "Jack", "bhgfh", "ghgh", "m"));
			repository.save(new Users("Jack3", "Bauer", "Jack", "bhgfh", "ghgh", "f"));

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Users customer : repository.findAll()) {
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			repository.findById(1L)
				.ifPresent(user -> {
					log.info("Customer found with findById(1L):");
					log.info("--------------------------------");
					log.info(user.toString());
					log.info("");
				});

			log.info("");
		};
	}

}
