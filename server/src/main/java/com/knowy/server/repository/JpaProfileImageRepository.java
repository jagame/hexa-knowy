package com.knowy.server.repository;

import com.knowy.server.entity.ProfileImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImageEntity, Integer>, ProfileImageRepository {

	@Override
	ProfileImageEntity findById(int id);
}
