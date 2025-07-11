package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.service.exception.*;
import com.knowy.server.service.model.MailMessage;
import com.knowy.server.util.EmailClientTool;
import com.knowy.server.util.exception.JwtKnowyException;
import com.knowy.server.util.exception.MailDispatchException;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.stereotype.Service;

@Service
public class UserFacadeService {

	private final EmailClientTool emailClientTool;
	private final PrivateUserService privateUserService;
	private final PublicUserService publicUserService;

	/**
	 * The constructor
	 *
	 * @param emailClientTool    the emailClientService
	 * @param privateUserService the userService
	 */
	public UserFacadeService(
		EmailClientTool emailClientTool,
		PrivateUserService privateUserService,
		PublicUserService publicUserService
	) {
		this.emailClientTool = emailClientTool;
		this.privateUserService = privateUserService;
		this.publicUserService = publicUserService;
	}

	// TODO - Añadir JavaDoc
	public PublicUserEntity registerNewUser(String nickname, String email, String password)
		throws InvalidUserException, ImageNotFoundException {
		PrivateUserEntity newPrivateUser = privateUserService.create(email, password);
		return publicUserService.create(nickname, newPrivateUser);
	}

	// TODO - Añadir JavaDoc
	public void updateNickname(String nickname, int userId)
		throws UserNotFoundException, NicknameAlreadyTakenException, UnchangedNicknameException {
		publicUserService.updateNickname(nickname, userId);
	}

	// TODO - Añadir JavaDoc
	public void updateProfileImage(int profilePictureId, int userId)
		throws UserNotFoundException, UnchangedImageException, ImageNotFoundException {
		publicUserService.updateProfileImage(profilePictureId, userId);
	}

	// TODO - Añadir JavaDoc
	public void updateLanguages(int userId, String[] languages) throws UserNotFoundException {
		publicUserService.updateLanguages(userId, languages);
	}

	// TODO - Añadir JavaDoc
	public void updatePassword(String token, String password, String confirmPassword)
		throws UserNotFoundException, JwtKnowyException, PasswordFormatException {
		privateUserService.resetPassword(token, password, confirmPassword);
	}

	// TODO - Añadir JavaDoc
	public void updateEmail(String email, int userId, String password)
		throws UserNotFoundException, UnchangedEmailException, WrongPasswordException {
		privateUserService.updateEmail(email, userId, password);
	}

	// TODO - Añadir JavaDoc
	public boolean isValidToken(String token) throws UserNotFoundException {
		return privateUserService.isValidToken(token);
	}

	// TODO - Añadir JavaDoc
	public void sendRecoveryPasswordEmail(String email, String recoveryBaseUrl)
		throws JwtKnowyException, UserNotFoundException, MailDispatchException {
		MailMessage mailMessage = privateUserService.createRecoveryPasswordEmail(email, recoveryBaseUrl);
		emailClientTool.sendEmail(mailMessage.to(), mailMessage.subject(), mailMessage.body());
	}
}
