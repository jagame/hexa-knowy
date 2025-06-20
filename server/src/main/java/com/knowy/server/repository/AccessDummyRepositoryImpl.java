package com.knowy.server.repository;

import com.knowy.server.entity.PrivateUser;
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

	@Override
	public PrivateUser findUserByEmail(String email) {
		PrivateUser privateUser = new PrivateUser();
		privateUser.setId(2L);
		privateUser.setUsername("ImATest");
		privateUser.setEmail(email);
		return privateUser;
	}

	@Override
	public boolean isTokenRegistered(String token) {
		return tokenMap.containsValue(token);
	}

	@Override
	public void saveToken(PrivateUser privateUser) {
		tokenMap.put(privateUser.getEmail(), privateUser.getToken());
	}

	@Override
	public Optional<PrivateUser> findUserByEmailAndPass(String email) {
		// Solo devolvemos el usuario si el email coincide
		if (email.equals("kn@gmail.com")) {
			PrivateUser user = new PrivateUser();
			user.setId(1L);
			user.setEmail("kn@gmail.com");
			user.setPassword("123");
			return Optional.of(user);
		}

		return Optional.empty();
	}

}
