package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaPrivateUserRepository extends JpaRepository<PrivateUserEntity, Integer>, PrivateUserRepository{
	@Override
	@Query("SELECT p from PrivateUserEntity p where p.email=:email")
	Optional<PrivateUserEntity> findByEmail(String email);

	@Override
	default void updateEmail(String email, String newEmail) {
		findByEmail(email).ifPresent(user -> {
			user.setEmail(newEmail);
			save(user);
		});
	}
}