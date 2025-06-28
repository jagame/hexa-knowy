package com.knowy.server.application.service;

import com.knowy.server.application.domain.error.KnowyException;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.port.gateway.MessageGateway;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.service.usecase.login.*;
import com.knowy.server.application.service.usecase.update.email.KnowyUserEmailUpdateException;
import com.knowy.server.application.service.usecase.update.email.UpdateUserEmailCommand;
import com.knowy.server.application.service.usecase.update.email.UpdateUserEmailUseCase;
import com.knowy.server.application.service.usecase.update.password.UpdateUserPasswordCommand;
import com.knowy.server.application.service.usecase.update.password.UpdateUserPasswordUseCase;

import java.util.Optional;

public class PrivateUserService {

	private final UpdateUserEmailUseCase updateUserEmailUseCase;
	private final LoginUseCase loginUseCase;
	private final PrivateUserRepository privateUserRepository;
	private final UpdateUserPasswordUseCase updateUserPasswordUseCase;
	private final MessageGateway messageGateway;

	public PrivateUserService(
		PrivateUserRepository privateUserRepository,
		TokenMapper tokenMapper,
		MessageGateway messageGateway
	) {
		this.updateUserEmailUseCase = new UpdateUserEmailUseCase(privateUserRepository);
		this.loginUseCase = new LoginUseCase(privateUserRepository, tokenMapper);
		this.updateUserPasswordUseCase = new UpdateUserPasswordUseCase();
		this.privateUserRepository = privateUserRepository;
		this.messageGateway = messageGateway;
	}

//	public void sendEmailWithToken(String email) {
//		if (isEmailRegistered(email)) {
//
//			PrivateUser user = accessRepository.findUserByEmail(email);
//			user.setToken(tokenService.createPasswordResetToken(user.getEmail(), user.getId()));
//
//			accessRepository.saveToken(user);
//
//			emailClientService.sendTokenToEmail(user.getToken(), user.getEmail());
//		}
//		System.out.println("Not exist email: " + email);
//	}

	public Optional<PrivateUser> findPrivateUserByEmail(String email) throws KnowyException {
		return privateUserRepository.findByEmail(email);
	}

	private boolean isEmailRegistered(String email) throws KnowyException {
		return privateUserRepository.findByEmail(email).isPresent();
	}

	public boolean isTokenRegistered(String token) throws KnowyException {
		return privateUserRepository.findByToken(token).isPresent();
	}

	public Void updateEmail(String email, String newEmail, String currentPassword) throws IllegalKnowyPasswordException, KnowyUserEmailUpdateException {
		return updateUserEmailUseCase.execute(new UpdateUserEmailCommand(email, newEmail,
			currentPassword));
	}

	public boolean updateUserPassword(String token, String oldPassword, String newPassword) throws KnowyException {
		return updateUserPasswordUseCase.execute(new UpdateUserPasswordCommand(oldPassword, newPassword, token));
	}

	public LoginResult doLogin(LoginCommand loginCommand)
		throws
		KnowyUserNotFoundException,
		IllegalKnowyPasswordException,
		KnowyUserLoginException {

		return loginUseCase.execute(loginCommand);
	}
}
