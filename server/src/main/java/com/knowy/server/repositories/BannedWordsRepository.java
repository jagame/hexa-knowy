package com.knowy.server.repositories;

import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class BannedWordsRepository {

	//List of banned words created for testing
	private final List<String> bannedWords = Arrays.asList(
		"tonto",
		"gay",
		"estupido"
	);

	public List<String> getBannedWords() {
		return bannedWords;
	}
}
