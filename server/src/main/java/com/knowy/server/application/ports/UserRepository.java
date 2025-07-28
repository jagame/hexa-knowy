package com.knowy.server.application.ports;

import com.knowy.server.application.domain.User;

import java.util.Optional;

public interface UserRepository {
	Optional<User> findById(Integer id);

	void updateNickname(String nickname, int id);

	<S extends User> S save(S user);

	Optional<User> findByNickname(String nickname);

	boolean existsByNickname(String nickname);
}
