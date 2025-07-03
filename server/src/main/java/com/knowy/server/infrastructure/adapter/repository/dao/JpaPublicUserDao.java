package com.knowy.server.infrastructure.adapter.repository.dao;

import com.knowy.server.infrastructure.adapter.repository.entity.JpaPublicUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaPublicUserDao extends JpaRepository<JpaPublicUserEntity, Integer> {

	Optional<JpaPublicUserEntity> findByNickname(String nickname);

}
