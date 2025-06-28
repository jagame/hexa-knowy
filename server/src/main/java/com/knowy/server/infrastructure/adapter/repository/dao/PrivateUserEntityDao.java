package com.knowy.server.infrastructure.adapter.repository.dao;

import com.knowy.server.infrastructure.adapter.repository.entity.PrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PrivateUserEntityDao extends JpaRepository<PrivateUserEntity, Integer> {

	Optional<PrivateUserEntity> findByEmail(String email);

	Optional<PrivateUserEntity> findByToken(String token);

}
