package com.knowy.server.application.service;

import com.knowy.server.application.domain.UserPrivate;
import com.knowy.server.application.domain.User;
import com.knowy.server.application.ports.UserPrivateRepository;
import com.knowy.server.application.service.exception.InvalidUserEmailException;
import com.knowy.server.application.service.exception.InvalidUserPasswordFormatException;
import com.knowy.server.application.service.exception.UnchangedEmailException;
import com.knowy.server.application.service.exception.UserNotFoundException;
import com.knowy.server.application.service.model.MailMessage;
import com.knowy.server.application.service.model.PasswordResetInfo;
import com.knowy.server.util.JwtTools;
import com.knowy.server.util.PasswordChecker;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

public class PrivateUserService {

	private final UserPrivateRepository privateUserRepository;
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
		UserPrivateRepository privateUserRepository,
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
	 * @param email    the email address to associate with the private user
	 * @param password the raw password to be encoded and stored
	 * @param user     the associated public user entity
	 * @return the created {@code PrivateUserEntity}
	 * @throws InvalidUserEmailException          if the email is already associated with another user
	 * @throws InvalidUserPasswordFormatException if the password does not meet formatting requirements
	 */
	public UserPrivate create(String email, String password, User user)
		throws InvalidUserPasswordFormatException, InvalidUserEmailException {

		if (findPrivateUserByEmail(email).isPresent()) {
			throw new InvalidUserEmailException("Email already exists");
		}

		if (!passwordChecker.isRightPasswordFormat(password)) {
			throw new InvalidUserPasswordFormatException("Invalid password format");
		}

		UserPrivate userPrivate = new UserPrivate(user);
		userPrivate.setEmail(email);
		userPrivate.setPassword(passwordEncoder.encode(password));

		return save(userPrivate);
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

		UserPrivate userPrivate = getPrivateUserById(userId);
		if (Objects.equals(email, userPrivate.email())) {
			throw new UnchangedEmailException("Email must be different from the current one.");
		}

		if (findPrivateUserByEmail(email).isPresent()) {
			throw new InvalidUserEmailException("The provided email is already associated with an existing account.");
		}

		passwordChecker.assertHasPassword(userPrivate, password);

		userPrivate.setEmail(email);
		privateUserRepository.save(userPrivate);
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

		UserPrivate userPrivate = verifyPasswordToken(token);

		userPrivate.setPassword(passwordEncoder.encode(password));
		save(userPrivate);
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
			verifyPasswordToken(token);
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
	public UserPrivate save(UserPrivate user) {
		return privateUserRepository.save(user);
	}

	/**
	 * Retrieves a private user entity by its ID if it exists.
	 *
	 * @param id the unique identifier of the private user
	 * @return an {@code Optional} containing the user if found, or empty if not
	 */
	public Optional<UserPrivate> findPrivateUserById(Integer id) {
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
	public UserPrivate getPrivateUserById(Integer id) throws UserNotFoundException {
		return findPrivateUserById(id)
			.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
	}

	/**
	 * Retrieves a private user entity by email if it exists.
	 *
	 * @param email the email address of the private user
	 * @return an {@code Optional} containing the user if found, or empty if not
	 */
	public Optional<UserPrivate> findPrivateUserByEmail(String email) {
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
	public UserPrivate getPrivateUserByEmail(String email) throws UserNotFoundException {
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

	/**
	 * Creates an email message with a recovery link for a deleted user account. The recovery link contains a token that
	 * expires after 30 days.
	 *
	 * @param email           the email address of the user whose account was deleted
	 * @param recoveryBaseUrl the base URL used to generate the account recovery link
	 * @return a {@link MailMessage} containing the recovery email details (receiver, subject, and body)
	 * @throws JwtKnowyException     if an error occurs while encoding the JWT token
	 * @throws UserNotFoundException if no user is found for the given email
	 */
	public MailMessage createDeletedAccountEmail(String email, String recoveryBaseUrl)
		throws JwtKnowyException, UserNotFoundException {

		final long THIRTY_DAYS_IN_MILLIS = 30L * 24 * 60 * 60 * 1000;

		String subject = "Tu enlace para recuperar la cuenta de Knowy está aquí";
		String token = createUserTokenByEmail(email, THIRTY_DAYS_IN_MILLIS);
		String body = reactivationTokenBody(token, recoveryBaseUrl);
		return new MailMessage(email, subject, body);
	}

	private String createUserTokenByEmail(String email, long tokenExpirationTime)
		throws UserNotFoundException, JwtKnowyException {
		UserPrivate userPrivate = findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(String.format("The user with email %s was not found", email)));

		PasswordResetInfo passwordResetInfo = new PasswordResetInfo(userPrivate.id(), userPrivate.email());
		return jwtTools.encode(passwordResetInfo, userPrivate.password(), tokenExpirationTime);
	}

	private String createUserTokenByEmail(String email) throws UserNotFoundException, JwtKnowyException {
		return createUserTokenByEmail(email, 600_000);
	}

	private String reactivationTokenBody(String token, String appUrl) {
		String url = "%s?token=%s".formatted(appUrl, token);
		return """
			¡Hola, %%$@€#&%%$%%!
			
			Tu cuenta de KNOWY ha sido desactivada correctamente.
			
			Dispones de 30 días para recuperarla haciendo click en el siguiente enlace:
			
			%s
			
			Una vez transcurrido este tiempo, tu cuenta será eliminada definitivamente.
			
			¡Esperamos verte de vuelta!
			
			© 2025 KNOWY, Inc
			""".formatted(url);
	}

	/**
	 * Deactivates a user account by verifying the provided password and confirmation password.
	 *
	 * @param email           the email address of the user whose account will be deactivated
	 * @param password        the password entered by the user for verification
	 * @param confirmPassword the confirmation password to ensure correctness
	 * @throws WrongPasswordException if the passwords do not match or the password is incorrect
	 * @throws UserNotFoundException  if no user is found for the given email
	 */
	public void desactivateUserAccount(
		String email,
		String password,
		String confirmPassword
	) throws WrongPasswordException, UserNotFoundException {
		if (!password.equals(confirmPassword)) {
			throw new WrongPasswordException("Passwords do not match");
		}

		UserPrivate userPrivate = findPrivateUserByEmail(email)
			.orElseThrow(() -> new UserNotFoundException("User not found"));

		passwordChecker.assertHasPassword(userPrivate, password);

		userPrivate.setActive(false);
		privateUserRepository.save(userPrivate);
	}

	/**
	 * Reactivates a user account based on a valid token.
	 *
	 * @param token the JWT token used to verify the reactivation request
	 * @throws JwtKnowyException     if the token is invalid or cannot be processed
	 * @throws UserNotFoundException if no user is associated with the token
	 */
	public void reactivateUserAccount(String token) throws JwtKnowyException, UserNotFoundException {
		if (!isValidToken(token)) {
			throw new JwtKnowyException("Invalid token");
		}

		UserPrivate userPrivate = verifyPasswordToken(token);

		if (!userPrivate.active()) {
			userPrivate.setActive(true);
			privateUserRepository.save(userPrivate);
		}
	}

	/**
	 * Verifies the authenticity and validity of a password reset token and retrieves the associated user.
	 *
	 * <p>This method performs two decoding steps:
	 * <ol>
	 *   <li>Unverified decoding to extract the user ID from the token payload.</li>
	 *   <li>Verified decoding using the user's password as the secret key to validate the token's integrity and expiration.</li>
	 * </ol>
	 * If both steps succeed, the method returns the corresponding {@code PrivateUserEntity}.</p>
	 *
	 * @param token the JWT token to verify
	 * @return the {@code PrivateUserEntity} linked to the token
	 * @throws JwtKnowyException     if the token is invalid, expired, or has been tampered with
	 * @throws UserNotFoundException if no user exists for the extracted user ID
	 */
	public UserPrivate verifyPasswordToken(String token) throws JwtKnowyException, UserNotFoundException {
		PasswordResetInfo passwordResetInfo = jwtTools.decodeUnverified(token, PasswordResetInfo.class);
		UserPrivate userPrivate = getPrivateUserById(passwordResetInfo.userId());
		jwtTools.decode(userPrivate.password(), token, PasswordResetInfo.class);
		return userPrivate;
	}
}
