package com.knowy.server.application.service;

import com.knowy.server.application.domain.UserPrivate;
import com.knowy.server.application.exception.KnowyMailDispatchException;
import com.knowy.server.application.exception.KnowyPasswordFormatException;
import com.knowy.server.application.exception.KnowyTokenException;
import com.knowy.server.application.exception.KnowyWrongPasswordException;
import com.knowy.server.application.ports.KnowyEmailClientTool;
import com.knowy.server.application.service.exception.*;
import com.knowy.server.application.service.model.MailMessage;
import com.knowy.server.application.service.model.NewUserCommand;

public class UserFacadeService {

	private final KnowyEmailClientTool knowyEmailClientTool;
	private final PrivateUserService privateUserService;
	private final UserService userService;

	/**
	 * The constructor
	 *
	 * @param knowyEmailClientTool the emailClientService
	 * @param privateUserService   the privateUserService
	 * @param publicUserService    the publicUserService
	 */
	public UserFacadeService(
		KnowyEmailClientTool knowyEmailClientTool,
		PrivateUserService privateUserService,
		UserService publicUserService
	) {
		this.knowyEmailClientTool = knowyEmailClientTool;
		this.privateUserService = privateUserService;
		this.userService = publicUserService;
	}

	/**
	 * Registers a new user by creating both public and private user entities.
	 *
	 * <p>This method first creates a public user with the given nickname,
	 * then links it with a private user using the provided email and password.</p>
	 *
	 * @param nickname the desired public nickname of the user
	 * @param email    the user's email address used for private identification
	 * @param password the user's password
	 * @return the created private user entity
	 * @throws KnowyInvalidUserException   if the user data is invalid or violates business rules
	 * @throws KnowyImageNotFoundException if a default profile image could not be found
	 */
	public UserPrivate registerNewUser(String nickname, String email, String password)
		throws KnowyInvalidUserException, KnowyImageNotFoundException {
		NewUserCommand newPublicUser = userService.create(nickname);
		return privateUserService.create(email, password, newPublicUser);
	}

	/**
	 * Updates the nickname of a public user.
	 *
	 * <p>Delegates the update to the public user service. If the new nickname
	 * is already taken or is identical to the current one, an exception is thrown.</p>
	 *
	 * @param nickname the new nickname to assign
	 * @param userId   the ID of the user whose nickname is to be updated
	 * @throws KnowyUserNotFoundException         if the user does not exist
	 * @throws KnowyNicknameAlreadyTakenException if the desired nickname is already in use
	 * @throws KnowyUnchangedNicknameException    if the new nickname is the same as the current one
	 */
	public void updateNickname(String nickname, int userId)
		throws KnowyUserNotFoundException, KnowyNicknameAlreadyTakenException, KnowyUnchangedNicknameException, KnowyInvalidUserNicknameException {
		userService.updateNickname(nickname, userId);
	}

	/**
	 * Updates the profile image of a public user.
	 *
	 * <p>Attempts to update the user's profile image using the provided image ID.
	 * If the new image is the same as the current one, or if the image is not found, exceptions are thrown.</p>
	 *
	 * @param profilePictureId the ID of the new profile image
	 * @param userId           the ID of the user whose profile image is to be updated
	 * @throws KnowyUserNotFoundException   if the user does not exist
	 * @throws KnowyUnchangedImageException if the new image is the same as the current image
	 * @throws KnowyImageNotFoundException  if the image with the given ID cannot be found
	 */
	public void updateProfileImage(int profilePictureId, int userId)
		throws KnowyUserNotFoundException, KnowyUnchangedImageException, KnowyImageNotFoundException {
		userService.updateProfileImage(profilePictureId, userId);
	}

	/**
	 * Updates the list of favorite categories for the specified public user.
	 *
	 * <p>Delegates the update operation to the {@code PublicUserService}.
	 * Replaces the user's current language list with the provided one.</p>
	 *
	 * @param userId    the ID of the user whose categories are to be updated
	 * @param languages an array of language representing the user's spoken categories
	 * @throws KnowyUserNotFoundException if the user does not exist
	 */
	public void updateLanguages(int userId, String[] languages) throws KnowyUserNotFoundException {
		userService.updateLanguages(userId, languages);
	}

	/**
	 * Updates a user's password using a password recovery token.
	 *
	 * <p>Validates the token, checks the password format and that both provided passwords match.
	 * If all checks pass, the user's password is updated.</p>
	 *
	 * @param token           the JWT token used to identify and authorize the password reset
	 * @param password        the new password
	 * @param confirmPassword confirmation of the new password
	 * @throws KnowyUserNotFoundException   if no user is associated with the token
	 * @throws KnowyTokenException          if the token is invalid or expired
	 * @throws KnowyPasswordFormatException if the password format is invalid or passwords do not match
	 */
	public void updatePassword(String token, String password, String confirmPassword)
		throws KnowyUserNotFoundException, KnowyTokenException, KnowyPasswordFormatException {
		privateUserService.resetPassword(token, password, confirmPassword);
	}

	/**
	 * Updates the email address of a private user after verifying the current password.
	 *
	 * <p>Delegates to {@code PrivateUserService} to perform the update.
	 * Ensures the new email is different and that the user's password is valid.</p>
	 *
	 * @param email    the new email address to set
	 * @param userId   the ID of the user whose email is being updated
	 * @param password the user's current password, used for authentication
	 * @throws KnowyUserNotFoundException   if the user does not exist
	 * @throws KnowyUnchangedEmailException if the new email is the same as the current one
	 * @throws KnowyWrongPasswordException  if the provided password is incorrect
	 */
	public void updateEmail(String email, int userId, String password)
		throws KnowyUserNotFoundException, KnowyUnchangedEmailException, KnowyWrongPasswordException, KnowyInvalidUserEmailException {
		privateUserService.updateEmail(email, userId, password);
	}

	/**
	 * Verifies whether the given password recovery token is valid.
	 *
	 * <p>Delegates the validation to the {@code PrivateUserService}, which checks
	 * the integrity, expiration, and user association of the token.</p>
	 *
	 * @param token the JWT token to validate
	 * @return {@code true} if the token is valid; {@code false} otherwise
	 * @throws KnowyUserNotFoundException if the token refers to a non-existent user
	 */
	public boolean isValidToken(String token) throws KnowyUserNotFoundException {
		return privateUserService.isValidToken(token);
	}

	/**
	 * Sends a password recovery email to the user associated with the given email address.
	 *
	 * <p>Generates a recovery link using the provided base URL and user-specific token,
	 * composes an email, and sends it using the configured email client.</p>
	 *
	 * @param email           the email address of the user requesting password recovery
	 * @param recoveryBaseUrl the base URL to be used in the recovery link (e.g., frontend reset page)
	 * @throws KnowyTokenException        if there is a problem generating the recovery token
	 * @throws KnowyUserNotFoundException if no user is associated with the given email
	 * @throws KnowyMailDispatchException if the email could not be sent
	 */
	public void sendRecoveryPasswordEmail(String email, String recoveryBaseUrl)
		throws KnowyTokenException, KnowyUserNotFoundException, KnowyMailDispatchException {
		MailMessage mailMessage = privateUserService.createRecoveryPasswordEmail(email, recoveryBaseUrl);
		knowyEmailClientTool.sendEmail(mailMessage.to(), mailMessage.subject(), mailMessage.body());
	}

	/**
	 * Deactivates the user account after validating the password and confirmation. Sends an email with the account
	 * recovery link using the provided recovery base URL.
	 *
	 * @param password        the user's current password for validation
	 * @param confirmPassword the confirmation of the password
	 * @param email           the email of the user whose account will be deactivated
	 * @param recoveryBaseUrl the base URL to be used for account reactivation link
	 * @throws KnowyUserNotFoundException  if the user with the given email does not exist
	 * @throws KnowyTokenException         if there is an error generating the JWT token
	 * @throws KnowyMailDispatchException  if sending the recovery email fails
	 * @throws KnowyWrongPasswordException if the password and confirmation do not match or are incorrect
	 */
	public void desactivateUserAccount(
		String password,
		String confirmPassword,
		String email,
		String recoveryBaseUrl
	) throws KnowyUserNotFoundException, KnowyTokenException, KnowyMailDispatchException, KnowyWrongPasswordException {
		privateUserService.desactivateUserAccount(email, password, confirmPassword);
		MailMessage mailMessage = privateUserService.createDeletedAccountEmail(email, recoveryBaseUrl);
		knowyEmailClientTool.sendEmail(mailMessage.to(), mailMessage.subject(), mailMessage.body());
	}

	/**
	 * Reactivates a previously deactivated user account using a valid reactivation token.
	 *
	 * @param token the JWT token used to verify and reactivate the user account
	 * @throws KnowyUserNotFoundException if the user associated with the token does not exist
	 * @throws KnowyTokenException        if the token is invalid or expired
	 */
	public void reactivateUserAccount(String token) throws KnowyUserNotFoundException, KnowyTokenException {
		privateUserService.reactivateUserAccount(token);
	}
}
