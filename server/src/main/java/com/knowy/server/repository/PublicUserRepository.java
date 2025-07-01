package com.knowy.server.repository;

import com.knowy.server.entity.PublicUserEntity;
import org.springframework.lang.NonNullApi;

import java.util.Optional;


public interface PublicUserRepository {
	Optional<PublicUserEntity> findUserById(Integer id);

	void updateNickname(String nickname, int id);

	<S extends PublicUserEntity> S save(S user);

	Optional<PublicUserEntity> findByNickname(String nickname);
}
