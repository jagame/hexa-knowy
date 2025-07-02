package com.knowy.server.repository;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.PublicUserEntity;

import java.util.Optional;
import java.util.Set;


public interface PublicUserRepository {
	Optional<PublicUserEntity> findUserById(Integer id);

	void updateNickname(String nickname, int id);

	<S extends PublicUserEntity> S save(S user);

	Optional<PublicUserEntity> findByNickname(String nickname);

	boolean existsByNickname(String nickname);

}
