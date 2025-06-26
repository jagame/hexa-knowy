package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;

import java.util.Optional;

public interface AuthRepository {
	Optional<PrivateUserEntity> findByEmail(String email);
}
