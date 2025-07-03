package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.infrastructure.adapter.repository.entity.JpaPrivateUserEntity;

public class AuthResultDto {
	private final JpaPrivateUserEntity user;
	private final String token;

	public AuthResultDto(JpaPrivateUserEntity user, String token) {
		this.user = user;
		this.token = token;
	}

	public JpaPrivateUserEntity getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}
}