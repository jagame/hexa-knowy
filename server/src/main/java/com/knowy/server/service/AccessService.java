package com.knowy.server.service;

import com.knowy.server.entity.PrivateUser;
import com.knowy.server.repository.AccessRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessService {

	AccessRepository accessRepository;
	TokenService tokenService;
	EmailClientService emailClientService;

	public AccessService(AccessRepository accessRepository, TokenService tokenService, EmailClientService emailClientService) {
		this.accessRepository = accessRepository;
		this.tokenService = tokenService;
		this.emailClientService = emailClientService;
	}

	public void sendEmailWithToken(String email) {
		if (isEmailRegistered(email)) {

			PrivateUser user = accessRepository.findUserByEmail(email);
			user.setToken(tokenService.createPasswordResetToken(user.getEmail(), user.getId()));

			accessRepository.saveToken(user);

			emailClientService.sendTokenToEmail(user.getToken(), user.getEmail());
		}
		System.out.println("Not exist email: " + email);
	}

	private boolean isEmailRegistered(String email) {
		return accessRepository.isEmailRegistered(email);
	}

	public boolean isTokenRegistered(String token) {
		return accessRepository.isTokenRegistered(token);
	}

	public void updateUserPassword(String token, String oldPassword, String newPassword) {
		//TODO - Implementar descrifrado de Token y verificar datos ocultos para cambiar los datos vía AccessRepository
	}

	public Optional<String> authenticateUser(String email, String password) {
		Optional<PrivateUser> foundUser = accessRepository.findUserByEmailAndPass(email);

		if (foundUser.isPresent()) {
			PrivateUser user = foundUser.get();

			if (user.getPassword().equals(password)) {
				String token = tokenService.createLoginToken(user.getEmail(), user.getId());
				return Optional.of(token);
			}
		}

		return Optional.empty();
	}
}
