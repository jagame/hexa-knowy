package com.knowy.server.controller.dto;

import lombok.Data;

@Data
public class UserConfigChangeEmailFormDto {
	private String email;
	private String password;
}
