package com.knowy.server.repository;

public interface UserConfigRepository {
	String findPasswordByEmail(String email);
	boolean checkPassword(String email, String password);


}
