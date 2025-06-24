package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessRepository {

	boolean isEmailRegistered(String email);

	PrivateUser findUserByUsername(String username);

	PrivateUser findUserByEmail(String email);

	boolean isTokenRegistered(String token);

	void saveToken(PrivateUser privateUser);
}
