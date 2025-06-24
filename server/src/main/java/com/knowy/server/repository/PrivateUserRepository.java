package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUser;
import com.knowy.server.entity.PublicUser;

import java.util.Optional;

public interface PrivateUserRepository {
	Optional<PrivateUser> findByEmail(String email);
	PrivateUser save(PrivateUser privateUser);
}
