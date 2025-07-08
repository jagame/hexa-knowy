package com.knowy.server.config;

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

	/**
	 * Defines a {@link PasswordEncoder} bean that uses BCrypt hashing algorithm.
	 * <p>
	 * This encoder is used by Spring Security to hash passwords securely before storing them and to verify password
	 * matches during authentication.
	 * </p>
	 *
	 * @return a {@link BCryptPasswordEncoder} instance for password encoding
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Configures the HTTP security settings for the application.
	 * <p>
	 * This includes authorization rules, form login, logout, and session management.
	 * </p>
	 *
	 * @param http the {@link HttpSecurity} object to configure
	 * @return the configured {@link SecurityFilterChain}
	 * @throws Exception if an error occurs during configuration
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(request -> request
			.requestMatchers("/fonts/**", "/scripts/**", "/styles/**", "/images/**", "/error/**").permitAll()
			.requestMatchers("/", "/login", "/register", "/password-change/email", "/password-change").permitAll()
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
		return http.build();
	}

}
