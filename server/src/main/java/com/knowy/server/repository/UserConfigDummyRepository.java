package com.knowy.server.repository;

import com.knowy.server.entity.UserConfiguration;
import org.springframework.stereotype.Repository;


@Repository
public class UserConfigDummyRepository implements UserConfigRepository {
	private final UserConfiguration userConfiguration = new UserConfiguration(1, "usuario123", "usuario@Privado123","12345aA@", "usuario123@correo.com", java.util.List.of("SQL", "JAVASCRIPT"));

	@Override
	public UserConfiguration findUserConfigByEmail(String email){
		return userConfiguration;
	}

	@Override
	public String findEmailByUsername(String username){
		return userConfiguration.getEmail();
	}

	@Override
	public String findPrivateUser(String email){
		return userConfiguration.getPrivateUsername();
	}

	@Override
	public void setEmail(String email){
		userConfiguration.setEmail(email);
	}

	@Override
	public String findPasswordByUsername(String username){
		return userConfiguration.getPassword();
	}

	@Override
	public void setPrivateUsername(String privateUsername){
		userConfiguration.setPrivateUsername(privateUsername);
	}




}
