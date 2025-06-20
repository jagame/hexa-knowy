package com.knowy.server.repository;

public class UserConfigDumRepository implements UserConfigRepository {
	@Override
	public String findPasswordByEmail(String email) {
		return "usuario123@correo.com";
	}

	@Override
	public boolean checkPassword(String email, String password) {
		return findPasswordByEmail(email).equals(password);
	}

}
