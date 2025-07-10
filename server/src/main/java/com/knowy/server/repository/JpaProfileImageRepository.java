package com.knowy.server.repository;

import com.knowy.server.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImageEntity, Integer>, ProfileImageRepository {

	@Override
	Optional<ProfileImageEntity> findById(int id);

}
