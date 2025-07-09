package com.knowy.server.repository;

import com.knowy.server.entity.ProfileImageEntity;

import java.util.Optional;

public interface ProfileImageRepository {

	Optional<ProfileImageEntity> findById(int id);


}
