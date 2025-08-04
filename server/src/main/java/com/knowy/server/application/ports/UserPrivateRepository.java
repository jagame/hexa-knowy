package com.knowy.server.application.ports;

import com.knowy.server.application.domain.UserPrivate;

import java.util.Optional;

public interface UserPrivateRepository {
	Optional<UserPrivate> findByEmail(String email);

	Optional<UserPrivate> findById(int id);

	UserPrivate save(UserPrivate user);
}
