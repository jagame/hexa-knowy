package com.knowy.server.repository.adapters;

import com.knowy.server.entity.PublicUserEntity;
import com.knowy.server.repository.ports.PublicUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
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
}