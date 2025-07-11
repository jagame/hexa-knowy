package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.exception.InvalidUserEmailException;
import com.knowy.server.service.exception.InvalidUserPasswordFormatException;
import com.knowy.server.service.exception.UnchangedEmailException;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.service.model.PasswordResetInfo;
import com.knowy.server.util.JwtTools;
import com.knowy.server.util.PasswordChecker;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class PrivateUserService {

	private final PrivateUserRepository privateUserRepository;
	private final PasswordEncoder passwordEncoder;
	private final PasswordChecker passwordChecker;
	private final JwtTools jwtTools;

	public PrivateUserService(
		PrivateUserRepository privateUserRepository,
		PasswordEncoder passwordEncoder,
		JwtTools jwtTools
	) {
		this.privateUserRepository = privateUserRepository;
		this.passwordChecker = new PasswordChecker(passwordEncoder);
		this.passwordEncoder = passwordEncoder;
		this.jwtTools = jwtTools;
	}

	public PrivateUserEntity create(String email, String password, PublicUserEntity publicUser)
		throws InvalidUserPasswordFormatException, InvalidUserEmailException {

		if (findPrivateUserByEmail(email).isPresent()) {
			throw new InvalidUserEmailException("Email alredy exists");
		}

		if (!passwordChecker.isRightPasswordFormat(password)) {
			throw new InvalidUserPasswordFormatException("Invalid password format");
		}

		PrivateUserEntity privateUser = new PrivateUserEntity();
		privateUser.setEmail(email);
		privateUser.setPassword(passwordEncoder.encode(password));
		privateUser.setPublicUserEntity(publicUser);

		return save(privateUser);
	}

	// TODO - JavaDoc
	// *
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException {
		PrivateUserEntity privateUser = getPrivateUserById(userId);
		if (Objects.equals(email, privateUser.getEmail())) {
			throw new UnchangedEmailException("Email must be different from the current one.");
		}
		passwordChecker.assertHasPassword(privateUser, password);

		privateUserRepository.updateEmail(privateUser.getEmail(), email);
	}

	// TODO - JavaDoc
	// *
	public void resetPassword(String token, String password, String confirmPassword)
		throws PasswordFormatException, JwtKnowyException, UserNotFoundException {

		passwordChecker.assertPasswordFormatIsRight(password);
		if (!password.equals(confirmPassword)) {
			throw new JwtKnowyException("Passwords do not match");
		}

		PrivateUserEntity privateUser = verifyPasswordResetToken(token);

		privateUser.setPassword(passwordEncoder.encode(password));
		save(privateUser);
	}

	public PrivateUserEntity verifyPasswordResetToken(String token) throws JwtKnowyException, UserNotFoundException {
		PasswordResetInfo passwordResetInfo = jwtTools.decodeUnverified(token, PasswordResetInfo.class);
		PrivateUserEntity privateUser = getPrivateUserById(passwordResetInfo.userId());
		jwtTools.decode(privateUser.getPassword(), token, PasswordResetInfo.class);
		return privateUser;
	}

	/**
	 * Validates a password reset JWT token by decoding and verifying its signature.
	 *
	 * <p>The method performs the following steps:
	 * <ul>
	 *     <li>Decodes the token without verifying the signature to extract the user ID.</li>
	 *     <li>Fetches the corresponding user from the database using the extracted ID.</li>
	 *     <li>Verifies the token's signature using the user's current password as the secret key.</li>
	 * </ul>
	 *
	 * <p>If all steps succeed, the token is considered valid. Otherwise, the method returns {@code false}.</p>
	 *
	 * @param token the JWT token to validate
	 * @return {@code true} if the token is well-formed, matches a known user, and its signature is valid; {@code false}
	 * otherwise
	 */
	public boolean isValidToken(String token) throws UserNotFoundException {
		try {
			verifyPasswordResetToken(token);
			return true;
		} catch (JwtKnowyException e) {
			return false;
		}
	}

	// TODO - JavaDoc
	public PrivateUserEntity save(PrivateUserEntity user) {
		return privateUserRepository.save(user);
	}

	// TODO - JavaDoc
	public Optional<PrivateUserEntity> findPrivateUserById(Integer id) {
		return privateUserRepository.findById(id);
	}

	// TODO - JavaDoc
	public PrivateUserEntity getPrivateUserById(Integer id) throws UserNotFoundException {
		return findPrivateUserById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
	}

	// TODO - JavaDoc
	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email) {
		return privateUserRepository.findByEmail(email);
	}

	// TODO - JavaDoc
	public PrivateUserEntity getPrivateUserByEmail(String email) throws UserNotFoundException {
		return findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	// TODO - JavaDoc
	public MailMessage createRecoveryPasswordEmail(String email, String recoveryBaseUrl)
		throws UserNotFoundException, JwtKnowyException {

		String subject = "Tu enlace para recuperar la cuenta de Knowy está aquí";
		String token = createUserTokenByEmail(email);
		String body = tokenBody(token, recoveryBaseUrl);
		return new MailMessage(email, subject, body);
	}

	private String createUserTokenByEmail(String email) throws UserNotFoundException, JwtKnowyException {
		PrivateUserEntity privateUser = findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(String.format("The user with email %s was not found", email)));

		PasswordResetInfo passwordResetInfo = new PasswordResetInfo(privateUser.getId(), privateUser.getEmail());
		return jwtTools.encode(passwordResetInfo, privateUser.getPassword());
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
}
