package com.knowy.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class KnowyServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KnowyServerApplication.class, args);
	}
}

