package com.knowy.server.service;

import com.knowy.server.controller.dto.UserDto;
import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.PrivateUserRepository;
import com.knowy.server.repository.PublicUserRepository;
import com.knowy.server.service.exception.InvalidUserException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccessService {

//	AccessRepository accessRepository;
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

	public PublicUserEntity registerNewUser(UserDto userDto) throws InvalidUserException {
		if (publicUserRepository.findByNickname(userDto.getUsername()).isPresent()){
			throw new InvalidUserException("El nombre de usuario ya existe.");
		}

		if (privateUserRepository.findByEmail(userDto.getEmail()).isPresent()){
			throw new InvalidUserException("El email ya está en uso.");
		}

		PrivateUserEntity privateUser = new PrivateUserEntity();
		privateUser.setEmail(userDto.getEmail());
		privateUser.setPassword(userDto.getPassword());
		// TODO - Change to token service
		PublicUserEntity publicUser = new PublicUserEntity();
		publicUser.setNickname(userDto.getUsername());

		privateUser.setPublicUserEntity(publicUser);
		publicUser.setPrivateUserEntity(privateUser);

//		privateUserRepository.save(privateUser);
		publicUser = publicUserRepository.save(publicUser);

		return publicUser;

	}
    //FIN CAMBIOS NICOEDRICOS



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
//	private boolean isEmailRegistered(String email) {
//		return accessRepository.isEmailRegistered(email);
//	}
//
//	public boolean isTokenRegistered(String token) {
//		return accessRepository.isTokenRegistered(token);
//	}

	public void updateUserPassword(String token, String oldPassword, String newPassword) {
		//TODO - Implementar descrifrado de Token y verificar datos ocultos para cambiar los datos vía AccessRepository
	}
}
