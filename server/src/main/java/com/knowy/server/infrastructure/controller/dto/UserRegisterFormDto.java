package com.knowy.server.infrastructure.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRegisterFormDto {
	@NotBlank(message = "El nickname no puede estar vacío")
	private String nickname;

	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "Formato de email inválido")
	private String email;

	private String password;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
