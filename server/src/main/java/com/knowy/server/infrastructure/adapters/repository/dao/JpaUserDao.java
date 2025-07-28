package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaUserDao extends JpaRepository<PublicUserEntity, Integer> {

	Optional<PublicUserEntity> findById(Integer integer);

	default void updateNickname(String nickname, int id) {
		findUserById(id).ifPresent(user -> {
			user.setNickname(nickname);
			save(user);
		});
	}

	@NonNull
	<S extends PublicUserEntity> S save(@NonNull S user);

	boolean existsByNickname(String nickname);
}