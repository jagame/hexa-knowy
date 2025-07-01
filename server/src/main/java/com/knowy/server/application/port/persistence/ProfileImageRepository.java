package com.knowy.server.application.port.persistence;

import com.knowy.server.infrastructure.adapter.repository.entity.ProfileImageEntity;

import java.util.Optional;

public interface ProfileImageRepository {

	Optional<ProfileImageEntity> findById(int id);

}
