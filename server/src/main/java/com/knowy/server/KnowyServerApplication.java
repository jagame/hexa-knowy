package com.knowy.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class KnowyServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(KnowyServerApplication.class, args);
/*
		// Accede al entorno
		Environment env = context.getEnvironment();

		// Lee el valor de la propiedad
		String text = env.getProperty("knowy.jwt.phrase");

		System.out.println("JWT Phrase: " + text);*/
	}
}

