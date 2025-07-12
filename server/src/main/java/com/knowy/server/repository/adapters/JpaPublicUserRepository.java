package com.knowy.server.repository.adapters;

import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.ports.PublicUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaPublicUserRepository extends PublicUserRepository, JpaRepository<PublicUserEntity, Integer> {
	@Override
	default Optional<PublicUserEntity> findUserById(Integer id) {
		return findById(id);
	}

	@Override
	default void updateNickname(String nickname, int id) {
		findUserById(id).ifPresent(user -> {
			user.setNickname(nickname);
			save(user);
		});
	}

	@Override
	@NonNull
	<S extends PublicUserEntity> S save(@NonNull S user);

	boolean existsByNickname(String nickname);

	@Override
	@Query("SELECT u.nickname FROM PublicUserEntity u WHERE u.id = :id")
	Optional<String> findNicknameById(@Param("id") Integer id);

	@Override
	@Query("SELECT u.profileImage.url FROM PublicUserEntity u WHERE u.id = :id")
	Optional<String> findProfileImageUrlById(Integer id);

	@Override
	@Query("SELECT u.profileImage FROM PublicUserEntity u where u.id = :id")
	Optional<ProfileImageEntity> findProfileImageByPublicUserId(@Param("id") Integer id);
}