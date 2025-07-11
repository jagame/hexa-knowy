package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.util.EmailClientTool;
import com.knowy.server.util.JwtTools;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.stereotype.Service;

@Service
public class AccessFacadeService {

	private final EmailClientTool emailClientTool;
	private final JwtTools jwtTools;
	private final PrivateUserService privateUserService;
	private final PublicUserService publicUserService;

	/**
	 * The constructor
	 *
	 * @param jwtTools           the jwtService
	 * @param emailClientTool    the emailClientService
	 * @param privateUserService the userService
	 */
	public AccessFacadeService(
		JwtTools jwtTools,
		EmailClientTool emailClientTool,
		PrivateUserService privateUserService,
		PublicUserService publicUserService
	) {
		this.jwtTools = jwtTools;
		this.emailClientTool = emailClientTool;
		this.privateUserService = privateUserService;
		this.publicUserService = publicUserService;
	}

	// TODO - Añadir JavaDoc
	// * Refactoriced
	public PublicUserEntity registerNewUser(String nickname, String email, String password)
		throws InvalidUserException, ImageNotFoundException {
		PrivateUserEntity newPrivateUser = privateUserService.create(email, password);
		return publicUserService.create(nickname, newPrivateUser);
	}

	public void updateNickname(String nickname, int userId)
		throws UserNotFoundException, NicknameAlreadyTakenException, UnchangedNicknameException {
		publicUserService.updateNickname(nickname, userId);
	}

	public void updateProfileImage(int profilePictureId, int userId)
		throws UserNotFoundException, UnchangedImageException, ImageNotFoundException {
		publicUserService.updateProfileImage(profilePictureId, userId);
	}

	public void updateLanguages(int userId, String[] languages) throws UserNotFoundException {
		publicUserService.updateLanguages(userId, languages);
	}

	// TODO - Añadir JavaDoc
	// * Refactorized qwq
	public void updatePassword(String token, String password, String confirmPassword)
		throws UserNotFoundException, JwtKnowyException, PasswordFormatException {
		privateUserService.resetPassword(token, password, confirmPassword);
	}

	// TODO - Añadir JavaDoc
	// * Refactorized qwq
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException {
		privateUserService.updateEmail(email, userId, password);
	}

	// TODO - Añadir JavaDoc
	// * Refactorized qwq
	public boolean isValidToken(String token) throws UserNotFoundException {
		return privateUserService.isValidToken(token);
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
		throws JwtKnowyException, UserNotFoundException, MailDispatchException {
		PrivateUserEntity privateUser = privateUserService.getPrivateUserByEmail(email);
		String token = jwtTools.encode(privateUserService.createPasswordResetInfo(email), privateUser.getPassword());
		MailMessage mailMessage = privateUserService.createRecoveryPasswordEmail(email, token, recoveryBaseUrl);
		emailClientTool.sendEmail(mailMessage.to(), mailMessage.subject(), mailMessage.body());
	}
}
