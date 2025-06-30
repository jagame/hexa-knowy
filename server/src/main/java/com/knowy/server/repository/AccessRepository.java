package com.knowy.server.repository;

import java.util.Optional;

public interface AccessRepository {

	boolean isEmailRegistered(String email);

//	PrivateUser findUserByEmail(String email);

	boolean isTokenRegistered(String token);

//	void saveToken(PrivateUser privateUser);

}
