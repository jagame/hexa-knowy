package com.knowy.server.controller.servicio;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@Service
public class UserService {
	private String username = "usuario123";
	private String privateUsername = "usuario@Privado123";
	private String email = "usuario123gmail.com";
	private String password = "12345aA@";

	public boolean validatePrivateUsername(String newPrivateUsername){
		return !(newPrivateUsername.equals(this.privateUsername));
	}

	public boolean validateCurrentPassword(String pass){
		return this.password.equals(pass);
	}

	public boolean validateEmail(String email){
		return !(this.email.equals(email));
	}




}
