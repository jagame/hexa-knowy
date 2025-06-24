package com.knowy.server.service;

import com.knowy.server.controller.dto.UserDto;
import com.knowy.server.entity.PrivateUser;
import com.knowy.server.entity.PublicUser;
import com.knowy.server.repository.*;
import com.knowy.server.service.exception.InvalidUserException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccessService {

	AccessRepository accessRepository;
//	TokenService tokenService;
//	EmailClientService emailClientService;

//	public AccessService(AccessRepository accessRepository, TokenService tokenService, EmailClientService emailClientService) {
//		this.accessRepository = accessRepository;
//		this.tokenService = tokenService;
//		this.emailClientService = emailClientService;
//	}

	private PrivateUserRepository privateUserRepository;
	private PublicUserRepository publicUserRepository;

	public AccessService(PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository) {
		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
	}

	public PublicUser registerNewUser(UserDto userDto) throws InvalidUserException {
		if (publicUserRepository.findByNickname(userDto.getUsername()).isPresent()){
			throw new InvalidUserException("El nombre de usuario ya existe.");
		}

		if (privateUserRepository.findByEmail(userDto.getEmail()).isPresent()){
			throw new InvalidUserException("El email ya está en uso.");
		}

		PrivateUser privateUser = new PrivateUser();
		privateUser.setEmail(userDto.getEmail());
		privateUser.setPassword(userDto.getPassword());
		// TODO - Change to token service
		privateUser.setToken(String.valueOf(UUID.randomUUID()));

		PublicUser publicUser = new PublicUser();
		publicUser.setNickname(userDto.getUsername());

		privateUser.setPublicUser(publicUser);
		publicUser.setPrivateUser(privateUser);

//		privateUserRepository.save(privateUser);
		publicUser = publicUserRepository.save(publicUser);

		return publicUser;

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
//
//	private boolean isUserNameRegistered(String username){
//		return accessRepository.findUserByUsername(username) != null;
//	}
	private boolean isEmailRegistered(String email) {
		return accessRepository.isEmailRegistered(email);
	}
//
//	public boolean isTokenRegistered(String token) {
//		return accessRepository.isTokenRegistered(token);
//	}

	public void updateUserPassword(String token, String oldPassword, String newPassword) {
		//TODO - Implementar descrifrado de Token y verificar datos ocultos para cambiar los datos vía AccessRepository
	}
}
