package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;


public interface PrivateUserRepository {
	PrivateUserEntity findByEmail(String email);
	void updateEmail(String email, String newEmail);


}
