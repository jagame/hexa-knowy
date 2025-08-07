package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaUserDao extends JpaRepository<PublicUserEntity, Integer> {

	@NonNull
	Optional<PublicUserEntity> findById(@NonNull Integer integer);

	@Query("SELECT pu FROM PublicUserEntity pu WHERE pu.nickname = :nickname")
	Optional<PublicUserEntity> findByNickname(@Param("nickname") String nickname);

	default void updateNickname(String nickname, int id) {
		findById(id).ifPresent(user -> {
			user.setNickname(nickname);
			save(user);
		});
	}

	@NonNull
	<S extends PublicUserEntity> S save(@NonNull S user);

	boolean existsByNickname(String nickname);
}