package com.knowy.server.service;

import com.knowy.server.entity.UserConfiguration;
import com.knowy.server.repository.UserConfigDummyRepository;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	private final UserConfigDummyRepository userConfigDummyRepository;

	public UserService(UserConfigDummyRepository userConfigDummyRepository) {
		this.userConfigDummyRepository = userConfigDummyRepository;
	}
	//checked
	public UserConfiguration getCurrentUser(String email){
		return userConfigDummyRepository.findUserConfigByEmail(email);
	}
	//checked
	public boolean validatePrivateUsername(String newPrivateUsername, String email){
		return !(newPrivateUsername.equals(userConfigDummyRepository.findPrivateUser(email)));
	}

	//checked
	public boolean updatePrivateUsername(String newPrivateUsername, String email){
		if(validatePrivateUsername(newPrivateUsername, email)){
			userConfigDummyRepository.setPrivateUsername(newPrivateUsername);
			return true;
		}
		return false;
	}

	//checked
	public boolean validateEqualEmail(String username, String newEmail){
		return(userConfigDummyRepository.findEmailByUsername(username).equals(newEmail));
	}

	//in process
	public boolean validateCurrentPassword(String currentPassword, String username){
		return(userConfigDummyRepository.findPasswordByUsername(username).equals(currentPassword));
	}

	//checked
	public boolean updateEmail(String username,  String newEmail, String currentPassword){
		if(validateEqualEmail(username, newEmail)&&validateCurrentPassword(currentPassword,username)){
			userConfigDummyRepository.setEmail(newEmail);
			return true;
		}
		return false;
	}





}
