package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;

public interface AccessRepository {

	boolean isEmailRegistered(String email);

	PrivateUserEntity findUserByEmail(String email);

	boolean isTokenRegistered(String token);

	void saveToken(PrivateUserEntity privateUserEntity);
}
