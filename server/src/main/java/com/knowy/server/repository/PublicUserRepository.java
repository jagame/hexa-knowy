package com.knowy.server.repository;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;


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
