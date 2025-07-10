package com.knowy.server.service;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.repository.LanguageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

	private final LanguageRepository languageRepository;

	public LanguageService(LanguageRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	public List<LanguageEntity> findAll() {
		return languageRepository.findAll();
	}
}
