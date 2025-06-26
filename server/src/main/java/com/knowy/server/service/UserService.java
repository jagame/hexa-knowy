package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.JpaPrivateUserRepository;
import com.knowy.server.repository.JpaPublicUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserService {
	private final JpaPrivateUserRepository jpaPrivateUserRepository;
	private final JpaPublicUserRepository jpaPublicUserRepository;


	public UserService(JpaPrivateUserRepository jpaPrivateUserRepository, JpaPublicUserRepository jpaPublicUserRepository) {
		this.jpaPrivateUserRepository = jpaPrivateUserRepository;
		this.jpaPublicUserRepository = jpaPublicUserRepository;
	}

	public Optional<PublicUserEntity> findPublicUserById(Integer id) {
		return jpaPublicUserRepository.findUserById(id);
	}

	public Optional<PrivateUserEntity> findPrivateUserByEmail(String email){
		return jpaPrivateUserRepository.findByEmail(email);
	}

	private boolean isValidNickname(String newNickname, PublicUserEntity user){
		return !(user.getNickname().equals(newNickname));
	}

	private boolean isValidEmail(String newEmail, PrivateUserEntity privateUser){
		return !(privateUser.getEmail().equals(newEmail));
	}

	private boolean isValidPassword(String currentPassword, PrivateUserEntity privateUser){
		return privateUser.getPassword().equals(currentPassword);
	}

	public boolean updateNickname(String newNickname, Integer id){
		Optional<PublicUserEntity> publicUser = findPublicUserById(id);
		if(publicUser.isPresent() && isValidNickname(newNickname, publicUser.get())){
			jpaPublicUserRepository.updateNickname(newNickname, id);
			return true;
		}
		return false;
	}

	public boolean updateEmail(String email,  String newEmail, String currentPassword){
		Optional<PrivateUserEntity> privateUser = findPrivateUserByEmail(email);
		if(privateUser.isPresent() && isValidEmail(newEmail, privateUser.get()) && isValidPassword(currentPassword, privateUser.get())){
			jpaPrivateUserRepository.updateEmail(email, newEmail);
			return true;
		}
		return false;
	}
}
