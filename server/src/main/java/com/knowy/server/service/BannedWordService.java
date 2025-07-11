package com.knowy.server.service;

import com.knowy.server.repository.BannedWordsRepository;

public class BannedWordService {

	private final BannedWordsRepository bannedWordsRepository;

	// TODO - JavaDoc
	public BannedWordService(BannedWordsRepository bannedWordsRepository) {
		this.bannedWordsRepository = bannedWordsRepository;
	}

	// TODO - JavaDoc
	public boolean isWordBanned(String word) {
		return bannedWordsRepository.isWordBanned(word);
	}
}
