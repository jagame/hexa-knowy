package com.knowy.server.infrastructure.config;

import com.knowy.server.application.port.persistence.QuizRepository;
import com.knowy.server.application.service.OptionQuizService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuizConfig {

	@Bean
	public OptionQuizService optionQuizService(QuizRepository quizRepository) {
		return new OptionQuizService(quizRepository);
	}

}
