package com.pvptowerdefense.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * The type Server application.
 */
@SpringBootApplication
@EnableJpaAuditing
public class ServerApplication {
	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
