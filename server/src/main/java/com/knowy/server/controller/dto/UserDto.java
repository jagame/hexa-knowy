package com.knowy.server.controller.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class UserDto {
	@NotBlank(message = "El nickname no pueede estar vacío")
	private String username;

	@NotBlank(message = "El email no puede estar vacío")
	@Email(message = "Formato de email inválido")
	private String email;


	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
