package com.knowy.server.repository;

import com.knowy.server.entity.PublicUserEntity;
import java.util.Optional;


public interface PublicUserRepository {
	Optional<PublicUserEntity> findUserById(Integer id);
	void updateNickname(String nickname, int id);
	Optional<PublicUserEntity> findByUsername(String username);
	boolean existsByUsername(String username);
}
