package com.knowy.server.repository.adapters;

import com.knowy.server.entity.ProfileImageEntity;
import com.knowy.server.repository.ports.ProfileImageRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaProfileImageRepository extends JpaRepository<ProfileImageEntity, Integer>, ProfileImageRepository {

	@Override
	Optional<ProfileImageEntity> findById(int id);

}
