package com.knowy.server.controller.dto;

import lombok.Data;

@Data
public class UserConfigSessionDTO {
	private String newEmail;
	private String email;
	private String currentPassword;
}
