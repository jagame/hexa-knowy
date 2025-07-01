package com.knowy.server.infrastructure.adapter.repository;

import com.knowy.server.application.port.persistence.ProfileImageRepository;
import com.knowy.server.infrastructure.adapter.repository.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImageEntity, Integer>, ProfileImageRepository {

	@Override
	Optional<ProfileImageEntity> findById(int id);
}
