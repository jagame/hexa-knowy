package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUser;

import java.util.Optional;

public interface AccessRepository {

	boolean isEmailRegistered(String email);

	PrivateUser findUserByEmail(String email);

	boolean isTokenRegistered(String token);

	void saveToken(PrivateUser privateUser);

	Optional<PrivateUser> findUserByEmailAndPass(String email);
}
