package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.service.model.PasswordResetInfo;
import com.knowy.server.util.EmailClientTool;
import com.knowy.server.util.JwtTools;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class AccessFacadeService {

	private final EmailClientTool emailClientTool;
	private final JwtTools jwtTools;
	private final UserService userService;
	private final PasswordEncoder passwordEncoder;

	/**
	 * The constructor
	 *
	 * @param jwtTools        the jwtService
	 * @param emailClientTool the emailClientService
	 * @param userService     the userService
	 * @param passwordEncoder the passwordEncoder
	 */
	public AccessFacadeService(
		JwtTools jwtTools,
		EmailClientTool emailClientTool,
		UserService userService,
		PasswordEncoder passwordEncoder
	) {
		this.jwtTools = jwtTools;
		this.emailClientTool = emailClientTool;
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;

	}

	// TODO - Añadir JavaDoc
	// * Refactoriced
	public PublicUserEntity registerNewUser(String nickname, String email, String password)
		throws InvalidUserException, ImageNotFoundException, UnchangedEmailException, UnchangedNicknameException {
		return userService.createUser(nickname, email, password);
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
	// TODO - Añadir JavaDoc
	// * Refactorized
	public void sendRecoveryPasswordEmail(String email, String recoveryBaseUrl)
		throws JwtKnowyException, UserNotFoundException, MailDispatchException
	{
		PrivateUserEntity privateUser = userService.getPrivateUserByEmail(email);
		String token = jwtTools.encode(userService.createPasswordResetInfo(email), privateUser.getPassword());
		MailMessage mailMessage = userService.createRecoveryPasswordEmail(email, token, recoveryBaseUrl);
		emailClientTool.sendEmail(mailMessage.to(), mailMessage.subject(), mailMessage.body());
	}

	/**
	 * Updates a user's password using a JWT token for verification.
	 *
	 * <p>This method performs several checks before updating the password:
	 * <ul>
	 *     <li>Ensures the new password and its confirmation match.</li>
	 *     <li>Decodes the provided JWT token to extract the user ID.</li>
	 *     <li>Validates the token using the user's current password as the secret.</li>
	 *     <li>Prevents reusing the current password.</li>
	 * </ul>
	 *
	 * <p>Once all validations pass, the user's password is updated in the system.</p>
	 *
	 * @param token           the JWT token used to authorize the password reset. This token must contain the user ID
	 *                        and be verifiable using the user's current password.
	 * @param password        the new password to set
	 * @param confirmPassword the confirmation of the new password; must match {@code password}
	 * @throws AccessException if any validation fails, the token is invalid or malformed, or the password update cannot
	 *                         be completed
	 */
	// TODO - Añadir JavaDoc
	// *
	public void updateUserPassword(String token, String password, String confirmPassword) throws
		AccessException, PasswordFormatException {
		try {
			passwordChecker.assertPasswordFormatIsRight(password);
			if (!password.equals(confirmPassword)) {
				throw new JwtKnowyException("Passwords do not match");
			}

			PasswordResetInfo passwordResetInfo = jwtTools.decodeUnverified(token, PasswordResetInfo.class);
			PrivateUserEntity privateUser = userService.findPrivateUserById(passwordResetInfo.getUserId())
				.orElseThrow(() -> new UsernameNotFoundException(String.format("The user with id %s was not found",
					passwordResetInfo.getUserId()))
				);
			jwtTools.decode(privateUser.getPassword(), token, PasswordResetInfo.class);

			privateUser.setPassword(passwordEncoder.encode(password));
			userService.save(privateUser);

		} catch (JwtKnowyException | NoSuchElementException | UsernameNotFoundException e) {
			throw new AccessException("Failed to decode and verify token", e);
		}
	}

	/**
	 * Updates the email of a user after validating identity and email change.
	 *
	 * <p>This method performs the following checks:
	 * <ul>
	 *   <li>Ensures a user with the given {@code userId} exists.</li>
	 *   <li>Verifies the new email is different from the current one.</li>
	 *   <li>Validates the provided password matches the user's current password.</li>
	 * </ul>
	 * If all checks pass, the user's email is updated in the repository.
	 *
	 * @param email    the new email to set for the user
	 * @param userId   the ID of the user whose email will be updated
	 * @param password the current password to authenticate the email change
	 * @throws UserNotFoundException   if no user exists with the given ID
	 * @throws UnchangedEmailException if the new email is the same as the current one
	 * @throws WrongPasswordException  if the provided password is incorrect
	 */
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException {
		PrivateUserEntity privateUser = userService.findPrivateUserById(userId)
			.orElseThrow(() -> new UserNotFoundException("User not found"));
		if (Objects.equals(email, privateUser.getEmail())) {
			throw new UnchangedEmailException("Email must be different from the current one.");
		}
		passwordChecker.assertHasPassword(privateUser, password);

		userService.updateEmailByEmail(privateUser.getEmail(), email);
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
	public boolean isValidToken(String token) {
		try {
			PasswordResetInfo passwordResetInfo = jwtTools.decodeUnverified(token, PasswordResetInfo.class);
			PrivateUserEntity privateUser = userService.findPrivateUserById(passwordResetInfo.getUserId())
				.orElseThrow();
			jwtTools.decode(privateUser.getPassword(), token, PasswordResetInfo.class);
			return true;
		} catch (JwtKnowyException | NoSuchElementException e) {
			return false;
		}
	}
}
