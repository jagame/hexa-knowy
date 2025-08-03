package com.knowy.server.infrastructure.config;

import com.knowy.server.application.ports.KnowyPasswordChecker;
import com.knowy.server.application.ports.KnowyPasswordEncoder;
import com.knowy.server.application.ports.KnowyTokenTools;
import com.knowy.server.application.ports.UserPrivateRepository;
import com.knowy.server.application.service.PrivateUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

	@Bean
	public KnowyPasswordEncoder knowyPasswordEncoder(PasswordEncoder passwordEncoder) {
		return passwordEncoder::encode;
	}

	@Bean
	public PrivateUserService privateUserService(
		UserPrivateRepository privateUserRepository,
		KnowyPasswordChecker knowyPasswordChecker,
		KnowyPasswordEncoder knowyPasswordEncoder,
		KnowyTokenTools knowyTokenTools
	) {
		return new PrivateUserService(
			privateUserRepository, knowyPasswordChecker, knowyPasswordEncoder, knowyTokenTools
		);
	}

}
