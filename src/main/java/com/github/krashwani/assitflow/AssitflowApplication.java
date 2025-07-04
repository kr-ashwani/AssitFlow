package com.github.krashwani.assitflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@SpringBootApplication
public class AssitflowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssitflowApplication.class, args);
	}

}
