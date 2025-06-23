package com.knowy.server.service;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.JpaPrivateUserRepository;
import com.knowy.server.repository.JpaPublicUserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	private final JpaPrivateUserRepository jpaPrivateUserRepository;
	private final JpaPublicUserRepository jpaPublicUserRepository;


	public UserService(JpaPrivateUserRepository jpaPrivateUserRepository, JpaPublicUserRepository jpaPublicUserRepository) {
		this.jpaPrivateUserRepository = jpaPrivateUserRepository;
		this.jpaPublicUserRepository = jpaPublicUserRepository;
	}

	public PrivateUserEntity getCurrentPrivateUser(String email){
		return jpaPrivateUserRepository.findByEmail(email);
	}
	public PublicUserEntity getCurrentPublicUser(Integer id){
		return jpaPublicUserRepository.findById(id).get();
	}

	public boolean validateNickname(String newNickname, Integer id){
		PublicUserEntity usuarioPublico = getCurrentPublicUser(id);
		return!(usuarioPublico.getNickname().equals(newNickname));
	}

	public boolean updateNickname(String newNickname, Integer id){
		if(validateNickname(newNickname, id)){
			jpaPublicUserRepository.updateNickname(newNickname, id);
			return true;
		}
		return false;
	}

	public boolean validateEqualEmail(String email, String newEmail){
		PrivateUserEntity usuarioPrivado = getCurrentPrivateUser(email);
		return!(usuarioPrivado.getEmail().equals(newEmail));
	}

	public boolean validateCurrentPassword(String currentPassword, String email){
		PrivateUserEntity usuarioPrivado = getCurrentPrivateUser(email);
		return (usuarioPrivado.getPassword().equals(currentPassword));
	}

	public boolean updateEmail(String email,  String newEmail, String currentPassword){
		if(validateEqualEmail(email, newEmail) && validateCurrentPassword(currentPassword, email)){
			jpaPrivateUserRepository.updateEmail(newEmail);
			return true;
		}
		return false;
	}
}
