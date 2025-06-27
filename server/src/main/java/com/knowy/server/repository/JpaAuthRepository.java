package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaAuthRepository extends JpaRepository<PrivateUserEntity, Long>,
	AuthRepository {
	@Override
	Optional<PrivateUserEntity> findByEmail(String email);
}