package com.knowy.server.repositories;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class ExistingUsernameRepository {

	//List of already existing usernames
	private final List<String> existingUsernames = Arrays.asList(
		"admin",
		"user",
		"root",
		"knowy"
	);

	public List<String> getExistingUsernames() {
		return existingUsernames;
	}
}
