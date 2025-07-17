package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.ports.PrivateUserRepository;
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

	/**
	 * The constructor
	 *
	 * @param privateUserRepository the privateUserRepository
	 * @param passwordEncoder       the passwordEncoder
	 * @param jwtTools              the jwtTools
	 */
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

	/**
	 * Creates and persists a new private user entity with the given email, password, and linked public user.
	 *
	 * <p>Performs validation to ensure the email is unique and the password meets formatting rules.
	 * If validations pass, the password is securely encoded and the user is saved.</p>
	 *
	 * @param email      the email address to associate with the private user
	 * @param password   the raw password to be encoded and stored
	 * @param publicUser the associated public user entity
	 * @return the created {@code PrivateUserEntity}
	 * @throws InvalidUserEmailException          if the email is already associated with another user
	 * @throws InvalidUserPasswordFormatException if the password does not meet formatting requirements
	 */
	public PrivateUserEntity create(String email, String password, PublicUserEntity publicUser)
		throws InvalidUserPasswordFormatException, InvalidUserEmailException {

		if (findPrivateUserByEmail(email).isPresent()) {
			throw new InvalidUserEmailException("Email already exists");
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

	/**
	 * Updates the email address of an existing private user after verifying the user's password.
	 *
	 * <p>Fails if the new email is the same as the current one or if the provided password is incorrect.
	 * After validation, updates the email and persists the user entity.</p>
	 *
	 * @param email    the new email address to assign
	 * @param userId   the ID of the user whose email is being updated
	 * @param password the current password used for verification
	 * @throws UserNotFoundException   if no user exists with the given ID
	 * @throws UnchangedEmailException if the new email is identical to the current one
	 * @throws WrongPasswordException  if the provided password is incorrect
	 */
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException, InvalidUserEmailException {
		findPrivateUserByEmail(email)
			.orElseThrow(() -> new InvalidUserEmailException("The provided email is already associated with an existing account."));

		PrivateUserEntity privateUser = getPrivateUserById(userId);
		if (Objects.equals(email, privateUser.getEmail())) {
			throw new UnchangedEmailException("Email must be different from the current one.");
		}
		passwordChecker.assertHasPassword(privateUser, password);

		privateUser.setEmail(email);
		privateUserRepository.save(privateUser);
	}

	/**
	 * Resets the password of a private user using a valid recovery token.
	 *
	 * <p>Validates the password format and confirms that both provided passwords match.
	 * Then verifies the token, retrieves the associated user, and updates the password after encoding it securely.</p>
	 *
	 * @param token           the JWT token used for password reset
	 * @param password        the new password to set
	 * @param confirmPassword confirmation of the new password
	 * @throws PasswordFormatException if the password does not meet the required format
	 * @throws JwtKnowyException       if the token is invalid or the passwords do not match
	 * @throws UserNotFoundException   if the user associated with the token does not exist
	 */
	public void resetPassword(String token, String password, String confirmPassword)
		throws PasswordFormatException, JwtKnowyException, UserNotFoundException {

		Objects.requireNonNull(password, "A password should be specified");

		passwordChecker.assertPasswordFormatIsRight(password);
		if (!password.equals(confirmPassword)) {
			throw new JwtKnowyException("Passwords do not match");
		}

		PrivateUserEntity privateUser = verifyPasswordResetToken(token);

		privateUser.setPassword(passwordEncoder.encode(password));
		save(privateUser);
	}

	/**
	 * Verifies the authenticity and validity of a password reset token and retrieves the corresponding user.
	 *
	 * <p>Performs two levels of decoding:
	 * <ul>
	 *   <li>First, an unverified decoding to extract the user ID from the token payload.</li>
	 *   <li>Second, a verified decoding using the user's password as the JWT secret to confirm token validity.</li>
	 * </ul>
	 * If both succeed, returns the user associated with the token.</p>
	 *
	 * @param token the JWT token to verify
	 * @return the {@code PrivateUserEntity} associated with the token
	 * @throws JwtKnowyException     if the token is invalid, expired, or tampered with
	 * @throws UserNotFoundException if no user exists for the extracted user ID
	 */
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
			Objects.requireNonNull(token, "A not null token is required");
			verifyPasswordResetToken(token);
			return true;
		} catch (JwtKnowyException e) {
			return false;
		}
	}

	/**
	 * Persists the given private user entity in the database.
	 *
	 * <p>If the user already exists, it will be updated; otherwise, a new record will be created.</p>
	 *
	 * @param user the {@code PrivateUserEntity} to persist
	 * @return the saved entity, potentially with an updated ID or other persisted fields
	 */
	public PrivateUserEntity save(PrivateUserEntity user) {
		return privateUserRepository.save(user);
	}

	/**
	 * Retrieves a private user entity by its ID if it exists.
	 *
	 * @param id the unique identifier of the private user
	 * @return an {@code Optional} containing the user if found, or empty if not
	 */
	public Optional<PrivateUserEntity> findPrivateUserById(Integer id) {
		return privateUserRepository.findById(id);
	}

	/**
	 * Retrieves a private user entity by its ID or throws an exception if not found.
	 *
	 * <p>This is a convenience method that wraps {@link #findPrivateUserById(Integer)}
	 * and ensures the result is non-null.</p>
	 *
	 * @param id the unique identifier of the private user
	 * @return the {@code PrivateUserEntity} if found
	 * @throws UserNotFoundException if no user exists with the given ID
	 */
	public PrivateUserEntity getPrivateUserById(Integer id) throws UserNotFoundException {
		return findPrivateUserById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
	}

	/**
	 * Retrieves a private user entity by email if it exists.
	 *
	 * @param email the email address of the private user
	 * @return an {@code Optional} containing the user if found, or empty if not
	 */
	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email) {
		return privateUserRepository.findByEmail(email);
	}

	/**
	 * Retrieves a private user entity by email or throws an exception if not found.
	 *
	 * <p>This method guarantees a non-null result and is typically used in flows
	 * where the presence of a user is required.</p>
	 *
	 * @param email the email address of the private user
	 * @return the {@code PrivateUserEntity} associated with the given email
	 * @throws UserNotFoundException if no user exists with the specified email
	 */
	public PrivateUserEntity getPrivateUserByEmail(String email) throws UserNotFoundException {
		return findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
	}

	/**
	 * Creates a recovery password email for the given user email.
	 *
	 * <p>Generates a JWT token associated with the user, builds the email content using the provided base URL,
	 * and returns a {@code MailMessage} object containing the recipient, subject, and body.</p>
	 *
	 * @param email           the email address of the user requesting password recovery
	 * @param recoveryBaseUrl the base URL to be used for building the recovery link
	 * @return a {@code MailMessage} ready to be sent
	 * @throws UserNotFoundException if no user exists with the given email
	 * @throws JwtKnowyException     if token generation fails
	 */
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
