package com.knowy.server.repository;

import com.knowy.server.entity.PublicUser;

import java.util.Optional;

public interface PublicUserRepository {
	Optional<PublicUser> findByNickname(String nickname);
	PublicUser save(PublicUser publicUser);
}
