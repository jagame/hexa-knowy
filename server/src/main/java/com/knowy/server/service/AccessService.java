package com.knowy.server.service;

import com.knowy.server.controller.dto.AuthResultDto;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.exception.AccessException;
import com.knowy.server.service.model.PasswordResetJwt;
import com.knowy.server.service.model.TokenTypeJwt;
import com.knowy.server.util.EmailClientService;
import com.knowy.server.util.JwtService;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessService {

	private final JwtService jwtService;
	private final EmailClientService emailClientService;
	private final PrivateUserRepository privateUserRepository;

	/**
	 * The constructor
	 *
	 * @param jwtService            the jwtService
	 * @param emailClientService    the emailClientService
	 * @param privateUserRepository the privateUserRepository
	 */
	public AccessService(JwtService jwtService, EmailClientService emailClientService, PrivateUserRepository privateUserRepository) {
		this.jwtService = jwtService;
		this.emailClientService = emailClientService;
		this.privateUserRepository = privateUserRepository;
	}

	/**
	 * Sends a password recovery email to the user associated with the given email address.
	 * <p>
	 * This method will:
	 * <ul>
	 *     <li>Find the user by email</li>
	 *     <li>Generate and update a new password recovery token</li>
	 *     <li>Send an email with the recovery URL (including the token)</li>
	 * </ul>
	 *
	 * @param email           the email address of the user requesting password recovery.
	 * @param recoveryBaseUrl the base URL to which the token will be appended (e.g.
	 *                        {@code https://example.com/recover-password}). The final URL will be
	 *                        {@code recoveryBaseUrl + "?token=" + token}.
	 * @throws AccessException if the user is not found or the email fails to send.
	 */
	public void sendRecoveryPasswordEmail(String email, String recoveryBaseUrl) throws AccessException {
		try {
			PrivateUserEntity user = privateUserRepository.findByEmail(email)
				.orElseThrow(() -> new UserNotFoundException(String.format("The user with email %s was not found", email)));

			updateUserToken(user);

			sendRecoveryToken(user, recoveryBaseUrl);
		} catch (UserNotFoundException | MailDispatchException | JwtKnowyException e) {
			throw new AccessException("Failed to send the password reset url to the user's email", e);
		}
	}

	private void updateUserToken(PrivateUserEntity user) throws JwtKnowyException {
		PasswordResetJwt passwordResetJwt = new PasswordResetJwt(user.getId(), user.getEmail(), TokenTypeJwt.PASSWORD_RESET);
		String newToken = jwtService.encode(passwordResetJwt, user.getPassword());
		user.setToken(newToken);
	}

	private void sendRecoveryToken(PrivateUserEntity user, String appUrl) throws MailDispatchException {
		String to = user.getEmail();
		String subject = "Tu enlace para recuperar la cuenta de Knowy está aquí";
		String body = tokenBody(user.getToken(), appUrl);

		emailClientService.sendEmail(to, subject, body);
	}

	private String tokenBody(String token, String appUrl) {
		String url = "%s?token=%s".formatted(appUrl, token);
		return """
			¡Hola, talentoso %%$@€#&%%$%%! 👋
			
			Sabemos que tu camino como $%%$@%%&€#@&$ es importante, por eso te ayudamos a recuperar tu acceso. \s
			Haz clic en el siguiente enlace para restablecer tu contraseña:
			
			%s
			
			Este enlace es válido solo por un tiempo limitado.
			Si no fuiste tú quien pidió este cambio, no te preocupes, simplemente ignora este correo.
			
			¡Sigue aprendiendo y conquistando tus metas con Knowy! 💪
			
			---
			© 2025 KNOWY, Inc
			""".formatted(url);
	}

	//TODO
	public void updateUserPassword(String token, String password, String confirmPassword) throws AccessException {
		try {
			if (!password.equals(confirmPassword)) {
				throw new JwtKnowyException("Passwords do not match");
			}

			PasswordResetJwt passwordResetJwt = jwtService.decodeUnverified(token, PasswordResetJwt.class);
			PrivateUserEntity privateUser = privateUserRepository.findById(passwordResetJwt.getUserId());
			jwtService.decode(privateUser.getPassword(), token, PasswordResetJwt.class);

			if (password.equals(privateUser.getPassword())) {
				throw new JwtKnowyException("Passwords can be equal to old password");
			}

			privateUser.setPassword(password);
			privateUserRepository.update(privateUser);
		} catch (JwtKnowyException e) {
			throw new AccessException("", e);
		}
	}

	public Optional<AuthResultDto> authenticateUser(String email, String password) {
		Optional<PrivateUserEntity> foundUser = privateUserRepository.findByEmail(email);

		if (foundUser.isPresent()) {
			PrivateUserEntity user = foundUser.get();
			if (user.getPassword().equals(password)) {
				String token = jwtService.createLoginToken(user.getEmail(), user.getId());
				return Optional.of(new AuthResultDto(user, token));
			}
		}
		return Optional.empty();
	}
}
