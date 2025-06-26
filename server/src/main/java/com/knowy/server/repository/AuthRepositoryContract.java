package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;

import java.util.Optional;

public interface AuthRepositoryContract {
	Optional<PrivateUserEntity> findUserByEmailWithPublicData(String email);
}
