package com.knowy.server;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.context.annotation.Bean;

public class Dummy {

	@Bean
	public ItemReader<String> lalalala() {
		return new JpaCursorItemReader<>();
	}

}
