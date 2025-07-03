package com.knowy.server.infrastructure.adapter.repository.dao;

import com.knowy.server.infrastructure.adapter.repository.entity.JpaPrivateUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPrivateUserEntityDao extends JpaRepository<JpaPrivateUserEntity, Integer> {

	Optional<JpaPrivateUserEntity> findByEmail(String email);

	Optional<JpaPrivateUserEntity> findByToken(String token);

}
