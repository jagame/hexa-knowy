package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.AccessRepository;
import com.knowy.server.repository.AuthRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessService {

	AccessRepository accessRepository;
	AuthRepository authRepository;
	TokenService tokenService;
	EmailClientService emailClientService;

	public AccessService(AccessRepository accessRepository, AuthRepository authRepository, TokenService tokenService, EmailClientService emailClientService) {
		this.accessRepository = accessRepository;
		this.authRepository = authRepository;
		this.tokenService = tokenService;
		this.emailClientService = emailClientService;
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

	private boolean isEmailRegistered(String email) {
		return accessRepository.isEmailRegistered(email);
	}

	public boolean isTokenRegistered(String token) {
		return accessRepository.isTokenRegistered(token);
	}

	public void updateUserPassword(String token, String oldPassword, String newPassword) {
		//TODO - Implementar descrifrado de Token y verificar datos ocultos para cambiar los datos vía AccessRepository
	}

//	public Optional<String> authenticateUser(String email, String password) {
//		Optional<PrivateUser> foundUser = accessRepository.findUserByEmailAndPwd(email);
//
//		if (foundUser.isPresent()) {
//			PrivateUser user = foundUser.get();
//
//			if (user.getPassword().equals(password)) {
//				String token = tokenService.createLoginToken(user.getEmail(), user.getId());
//				return Optional.of(token);
//			}
//		}
//
//		return Optional.empty();
//	}

	public Optional<String> authenticateUser(String email, String password, HttpSession session) {
		// Buscar el usuario por correo, incluyendo los datos públicos
		Optional<PrivateUserEntity> foundUser = authRepository.findUserByEmailWithPublicData(email);
		// Si el usuario existe, proceder a verificar la contraseña
		if (foundUser.isPresent()) {
			PrivateUserEntity user = foundUser.get();
			// Comparar la contraseña ingresada con la almacenada
			if (user.getPassword().equals(password)) {
				// Generar un token de autenticación único para este usuario
				String token = tokenService.createLoginToken(user.getEmail(), user.getId().longValue());
				// Almacenar en la sesión HTTP los atributos que indican que el usuario ha iniciado sesión correctamente
				session.setAttribute("loggedUser", user);
				session.setAttribute("authToken", token);
				// Devolver el token envuelto en Optional para indicar éxito
				return Optional.of(token);
			}
		}
		// Si no existe el usuario o la contraseña no coincide, devolver vacío
		return Optional.empty();
	}
}
