package com.knowy.server.infrastructure.adapters.persistence.dao;

import com.knowy.server.infrastructure.adapters.persistence.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProfileImageDao extends JpaRepository<ProfileImageEntity, Integer> {

	Optional<ProfileImageEntity> findById(int id);
}
