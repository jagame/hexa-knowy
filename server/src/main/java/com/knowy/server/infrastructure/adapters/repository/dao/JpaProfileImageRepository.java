package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImageEntity, Integer> {

	Optional<ProfileImageEntity> findById(int id);
}
