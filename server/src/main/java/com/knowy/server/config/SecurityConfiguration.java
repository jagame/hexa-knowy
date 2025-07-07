package com.knowy.server.config;

import com.knowy.server.service.UserSecurityDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http, UserSecurityDetailsService userSecurityDetailsService) throws Exception {
		http.authorizeHttpRequests(request -> request
			.requestMatchers("/fonts/**", "/scripts/**", "/styles/**", "/images/**", "/error/**").permitAll()
			.requestMatchers("/", "/login", "/register", "password-change/email").permitAll()
			.anyRequest().authenticated()
		);

		http.formLogin(form -> form
			.loginPage("/login")
			.usernameParameter("email")
			.passwordParameter("password")
			.loginProcessingUrl("/login")
			.failureUrl("/login?error")
			.defaultSuccessUrl("/home")
			.permitAll());

		http.logout(logout -> logout
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
			.deleteCookies("JSESSIONID")
			.permitAll());

		http.sessionManagement(session -> session
			.maximumSessions(1)
		);

		http.exceptionHandling(exceptions -> exceptions
			.accessDeniedPage("/403")
		);
		return http.build();
	}

}
