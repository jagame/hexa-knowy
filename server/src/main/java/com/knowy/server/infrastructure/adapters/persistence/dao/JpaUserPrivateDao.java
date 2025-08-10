package com.knowy.server.infrastructure.adapters.persistence.dao;

import com.knowy.server.infrastructure.adapters.persistence.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaUserPrivateDao extends JpaRepository<PrivateUserEntity, Integer> {
	Optional<PrivateUserEntity> findByEmail(String email);

	@NonNull
	<S extends PrivateUserEntity> S save(@NonNull S user);

	Optional<PrivateUserEntity> findById(int id);
}