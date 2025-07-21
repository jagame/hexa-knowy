package com.knowy.server.repository.adapters;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.repository.ports.PrivateUserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPrivateUserRepository extends JpaRepository<PrivateUserEntity, Integer>, PrivateUserRepository {
	@Override
	Optional<PrivateUserEntity> findByEmail(String email);

	@Override
	@NonNull
	<S extends PrivateUserEntity> S save(@NonNull S user);

	@Override
	Optional<PrivateUserEntity> findById(int id);
}