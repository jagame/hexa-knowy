package com.knowy.server.infrastructure.config;

import com.knowy.server.application.port.gateway.MessageDispatcher;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.persistence.PublicUserRepository;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.service.PrivateUserService;
import com.knowy.server.application.service.PublicUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServicesConfig {

	@Bean
	public PublicUserService publicUserService(PublicUserRepository publicUserRepository) {
		return new PublicUserService(publicUserRepository);
	}

	@Bean
	public PrivateUserService privateUserService(
		PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository,
		TokenMapper tokenMapper, MessageDispatcher messageDispatcher
	) {
		return new PrivateUserService(
			privateUserRepository,
			publicUserRepository,
			tokenMapper,
			messageDispatcher
		);
	}

}
