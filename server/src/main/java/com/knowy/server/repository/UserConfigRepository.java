package com.knowy.server.repository;

import com.knowy.server.entity.UserConfiguration;

public interface UserConfigRepository{
	UserConfiguration findUserConfigByEmail(String email);
	String findEmailByUsername(String username);
	String findPrivateUser(String email);
	void setEmail(String email);
	public String findPasswordByUsername(String username);
	void setPrivateUsername(String privateUsername);

}
