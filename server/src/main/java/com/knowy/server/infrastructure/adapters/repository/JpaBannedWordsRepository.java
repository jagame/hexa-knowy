package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.ports.BannedWordsRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaBannedWordsDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaBannedWordsRepository implements BannedWordsRepository {

	private final JpaBannedWordsDao jpaBannedWordsDao;

	public JpaBannedWordsRepository(JpaBannedWordsDao jpaBannedWordsDao) {
		this.jpaBannedWordsDao = jpaBannedWordsDao;
	}

	@Override
	public boolean isWordBanned(String word) {
		return jpaBannedWordsDao.isWordBanned(word);
	}
}
