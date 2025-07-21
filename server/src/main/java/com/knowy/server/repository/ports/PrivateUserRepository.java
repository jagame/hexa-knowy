package com.knowy.server.repository.ports;

import com.knowy.server.entity.PrivateUserEntity;

import java.util.Optional;


public interface PrivateUserRepository {
	Optional<PrivateUserEntity> findByEmail(String email);

	<S extends PrivateUserEntity> S save(S user);

	Optional<PrivateUserEntity> findById(int id);
}
