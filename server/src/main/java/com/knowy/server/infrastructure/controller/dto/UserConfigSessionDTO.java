package com.knowy.server.infrastructure.controller.dto;

import lombok.Data;

@Data
public class UserConfigSessionDTO {
	private String newEmail;
	private String currentPassword;
}
