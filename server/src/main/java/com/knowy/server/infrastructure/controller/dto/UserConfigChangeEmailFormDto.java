package com.knowy.server.infrastructure.controller.dto;

import lombok.Data;

@Data
public class UserConfigChangeEmailFormDto {
	private String email;
	private String password;
}
