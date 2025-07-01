package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.application.domain.PrivateUser;

import java.io.Serializable;

public record SessionUser(int id, String email, String nickname, String profileImageUrl) implements Serializable {

	public SessionUser(PrivateUser user) {
		this(
			user.id(),
			user.email().value(),
			user.nickname(),
			user.profileImage().url()
		);
	}
}
