package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.ExerciseDifficult;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.util.EmailClientTool;
import com.knowy.server.util.exception.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserFacadeService {

	private final EmailClientTool emailClientTool;
	private final PrivateUserService privateUserService;
	private final PublicUserService publicUserService;
	private final PublicUserExerciseService publicUserExerciseService;

	/**
	 * The constructor
	 *
	 * @param emailClientTool           the emailClientService
	 * @param privateUserService        the privateUserService
	 * @param publicUserService         the publicUserService
	 * @param publicUserExerciseService the publicUserExerciseService
	 */
	public UserFacadeService(
		EmailClientTool emailClientTool,
		PrivateUserService privateUserService,
		PublicUserService publicUserService,
		PublicUserExerciseService publicUserExerciseService) {
		this.emailClientTool = emailClientTool;
		this.privateUserService = privateUserService;
		this.publicUserService = publicUserService;
		this.publicUserExerciseService = publicUserExerciseService;
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
	 * @throws InvalidUserException   if the user data is invalid or violates business rules
	 * @throws ImageNotFoundException if a default profile image could not be found
	 */
	public PrivateUserEntity registerNewUser(String nickname, String email, String password)
		throws InvalidUserException, ImageNotFoundException {
		PublicUserEntity newPublicUser = publicUserService.create(nickname);
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
	 * @throws UserNotFoundException         if the user does not exist
	 * @throws NicknameAlreadyTakenException if the desired nickname is already in use
	 * @throws UnchangedNicknameException    if the new nickname is the same as the current one
	 */
	public void updateNickname(String nickname, int userId)
		throws UserNotFoundException, NicknameAlreadyTakenException, UnchangedNicknameException, InvalidUserNicknameException {
		publicUserService.updateNickname(nickname, userId);
	}

	/**
	 * Updates the profile image of a public user.
	 *
	 * <p>Attempts to update the user's profile image using the provided image ID.
	 * If the new image is the same as the current one, or if the image is not found, exceptions are thrown.</p>
	 *
	 * @param profilePictureId the ID of the new profile image
	 * @param userId           the ID of the user whose profile image is to be updated
	 * @throws UserNotFoundException   if the user does not exist
	 * @throws UnchangedImageException if the new image is the same as the current image
	 * @throws ImageNotFoundException  if the image with the given ID cannot be found
	 */
	public void updateProfileImage(int profilePictureId, int userId)
		throws UserNotFoundException, UnchangedImageException, ImageNotFoundException {
		publicUserService.updateProfileImage(profilePictureId, userId);
	}

	/**
	 * Updates the list of favorite languages for the specified public user.
	 *
	 * <p>Delegates the update operation to the {@code PublicUserService}.
	 * Replaces the user's current language list with the provided one.</p>
	 *
	 * @param userId    the ID of the user whose languages are to be updated
	 * @param languages an array of language representing the user's spoken languages
	 * @throws UserNotFoundException if the user does not exist
	 */
	public void updateLanguages(int userId, String[] languages) throws UserNotFoundException {
		publicUserService.updateLanguages(userId, languages);
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
	 * @throws UserNotFoundException   if no user is associated with the token
	 * @throws JwtKnowyException       if the token is invalid or expired
	 * @throws PasswordFormatException if the password format is invalid or passwords do not match
	 */
	public void updatePassword(String token, String password, String confirmPassword)
		throws UserNotFoundException, JwtKnowyException, PasswordFormatException {
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
	 * @throws UserNotFoundException   if the user does not exist
	 * @throws UnchangedEmailException if the new email is the same as the current one
	 * @throws WrongPasswordException  if the provided password is incorrect
	 */
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException, InvalidUserEmailException {
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
	 * @throws UserNotFoundException if the token refers to a non-existent user
	 */
	public boolean isValidToken(String token) throws UserNotFoundException {
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
	 * @throws JwtKnowyException     if there is a problem generating the recovery token
	 * @throws UserNotFoundException if no user is associated with the given email
	 * @throws MailDispatchException if the email could not be sent
	 */
	public void sendRecoveryPasswordEmail(String email, String recoveryBaseUrl)
		throws JwtKnowyException, UserNotFoundException, MailDispatchException {
		MailMessage mailMessage = privateUserService.createRecoveryPasswordEmail(email, recoveryBaseUrl);
		emailClientTool.sendEmail(mailMessage.to(), mailMessage.subject(), mailMessage.body());
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity getNextExercise(int userId, int lessonId) throws ExerciseNotFoundException {
		return publicUserExerciseService.getNextExerciseByLessonId(userId, lessonId);
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity getNextExercise(int userId) throws ExerciseNotFoundException {
		return publicUserExerciseService.getNextExerciseByUserId(userId);
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity getPublicUserExerciseById(int userId, int exerciseId) throws ExerciseNotFoundException {
		return publicUserExerciseService.getById(userId, exerciseId);
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity processUserAnswer(ExerciseDifficult exerciseDifficult, PublicUserExerciseEntity exerciseEntity) {
		publicUserExerciseService.difficultSelect(exerciseDifficult, exerciseEntity);
		return publicUserExerciseService.save(exerciseEntity);
	}
}
