package com.knowy.server.controller.dto;

import com.knowy.server.entity.PrivateUserEntity;

import java.io.Serializable;

public record SessionUser(int id, String email, String nickname, String profileImageUrl) implements Serializable {

	public SessionUser(PrivateUserEntity user) {
		this(
			user.getId(),
			user.getEmail(),
			user.getPublicUserEntity().getNickname(),
			user.getPublicUserEntity().getProfileImage().getUrl()
		);
	}
}
