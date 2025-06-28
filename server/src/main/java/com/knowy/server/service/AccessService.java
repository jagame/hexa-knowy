package com.knowy.server.service;

import com.knowy.server.controller.dto.AuthResultDto;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.JpaAuthRepository;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.service.exception.MailDispatchException;
import com.knowy.server.service.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessService {

	private final JpaAuthRepository authRepository;
	private final TokenService tokenService;
	private final EmailClientService emailClientService;
	private final PrivateUserRepository privateUserRepository;

	public AccessService(JpaAuthRepository authRepository, TokenService tokenService, EmailClientService emailClientService, PrivateUserRepository privateUserRepository) {
		this.authRepository = authRepository;
		this.tokenService = tokenService;
		this.emailClientService = emailClientService;
		this.privateUserRepository = privateUserRepository;
	}

	public void sendEmailWithToken(String email, String appUrl) throws UserNotFoundException,
		MailDispatchException {
		PrivateUserEntity user = privateUserRepository.findByEmail(email)
			.orElseThrow(() -> new UserNotFoundException(String.format("The user with email %s was not found", email)));

		// TODO - Extraer a otro método para updatear
		String newToken = tokenService.createPasswordResetToken(user.getEmail(), user.getId());
		user.setToken(newToken);

		privateUserRepository.update(user);

		emailClientService.sendTokenToEmail(user.getToken(), user.getEmail(), appUrl);
	}

	public boolean isTokenRegistered(String token) {
		String privateUserToken = privateUserRepository.findByToken(token).getToken();
		return token.equals(privateUserToken);
	}

	public void updateUserPassword(String token, String oldPassword, String newPassword) {
		//TODO - Implementar descrifrado de Token y verificar datos ocultos para cambiar los datos vía AccessRepository
	}

	public Optional<AuthResultDto> authenticateUser(String email, String password) {
		Optional<PrivateUserEntity> foundUser = authRepository.findByEmail(email);

		if (foundUser.isPresent()) {
			PrivateUserEntity user = foundUser.get();
			if (user.getPassword().equals(password)) {
				String token = tokenService.createLoginToken(user.getEmail(), user.getId());
				return Optional.of(new AuthResultDto(user, token));
			}
		}
		return Optional.empty();
	}
}
