package com.knowy.server.repositories;

import com.knowy.server.entities.UserEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepositoryTest {

	private UserEntity testUser;

	public UserRepositoryTest() {
		this.testUser = new UserEntity("userTest", "https://placehold.co/50x50", new ArrayList<>());
	}

	public UserEntity getTestUser() {
		return testUser;
	}

	public void save(UserEntity user) {
		this.testUser = user;
	}
}
