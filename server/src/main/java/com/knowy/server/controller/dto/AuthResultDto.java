package com.knowy.server.controller.dto;

import com.knowy.server.entity.PrivateUserEntity;

public class AuthResultDto {
	private final PrivateUserEntity user;
	private final String token;

	public AuthResultDto(PrivateUserEntity user, String token) {
		this.user = user;
		this.token = token;
	}

	public PrivateUserEntity getUser() {
		return user;
	}

	public String getToken() {
		return token;
	}
}