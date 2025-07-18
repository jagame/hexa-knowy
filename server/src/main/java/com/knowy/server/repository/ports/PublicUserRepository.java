package com.knowy.server.repository.ports;

import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;

import java.util.Optional;

public interface PublicUserRepository {
	Optional<PublicUserEntity> findUserById(Integer id);

	void updateNickname(String nickname, int id);

	<S extends PublicUserEntity> S save(S user);

	Optional<PublicUserEntity> findByNickname(String nickname);

	boolean existsByNickname(String nickname);

	Optional<String> findNicknameById(Integer id);

	Optional<ProfileImageEntity> findProfileImageByPublicUserId(Integer id);

	Optional<String> findProfileImageUrlById(Integer id);
}
