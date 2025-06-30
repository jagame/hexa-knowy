package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface JpaPrivateUserRepository extends PrivateUserRepository, JpaRepository<PrivateUserEntity, Integer> {
	@Override
	Optional<PrivateUserEntity> findByEmail(String email);

	@Override
	default void updateEmail(String email, String newEmail) {
		findByEmail(email).ifPresent(user -> {
			user.setEmail(newEmail);
			save(user);
		});
	}

	@Override
	@NonNull
	<S extends PrivateUserEntity> S save(@NonNull S user);

	@Override
	PrivateUserEntity findById(int id);
}
