package com.knowy.server.application.ports;

import com.knowy.server.domain.ProfileImage;

import java.util.Optional;

public interface ProfileImageRepository {
	Optional<ProfileImage> findById(int id);
}
