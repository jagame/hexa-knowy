package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUserEntity;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
/*
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

	@Override
	public PrivateUserEntity findUserByEmail(String email) {
		PrivateUserEntity privateUserEntity = new PrivateUserEntity();
		privateUserEntity.setId(2L);
		privateUserEntity.setUsername("ImATest");
		privateUserEntity.setEmail(email);
		return privateUserEntity;
	}

	@Override
	public boolean isTokenRegistered(String token) {
		return tokenMap.containsValue(token);
	}

	@Override
	public void saveToken(PrivateUserEntity privateUserEntity) {
		tokenMap.put(privateUserEntity.getEmail(), privateUserEntity.getToken());
	}
}*/
