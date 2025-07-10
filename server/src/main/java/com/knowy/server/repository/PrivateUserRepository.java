package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;

import java.util.Optional;


public interface PrivateUserRepository {
	Optional<PrivateUserEntity> findByEmail(String email);

	void updateEmail(String email, String newEmail);

	<S extends PrivateUserEntity> S save(S user);

	Optional<PrivateUserEntity> findById(int id);
}
