package com.knowy.server.repository;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class AccessDummyRepositoryImpl implements AccessRepository {

	Map<String, String> tokenMap = new HashMap<>();

	{
		tokenMap.put("kn@gmail.com", "access_token");
	}

	@Override
	public boolean isEmailRegistered(String email) {
		return tokenMap.containsKey(email);
	}

//	@Override
//	public PrivateUser findUserByEmail(String email) {
//		PrivateUser privateUser = new PrivateUser();
//		privateUser.setId(2L);
//		privateUser.setUsername("ImATest");
//		privateUser.setEmail(email);
//		return privateUser;
//	}

	@Override
	public boolean isTokenRegistered(String token) {
		return tokenMap.containsValue(token);
	}

//	@Override
//	public void saveToken(PrivateUser privateUser) {
//		tokenMap.put(privateUser.getEmail(), privateUser.getToken());
//	}
}
