package com.knowy.server.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class UserService {
	private String username = "usuario123";
	private String privateUsername = "usuario@Privado123";
	private String email = "usuario123@gmail.com";
	private String password = "12345aA@";

	public boolean validatePrivateUsername(String newPrivateUsername) {
		return !(newPrivateUsername.equals(this.privateUsername));
	}

	public boolean validateCurrentPassword(String pass) {
		return this.password.equals(pass);
	}

	public boolean validateEqualEmail(String email) {
		return !(this.email.equals(email));
	}

}
