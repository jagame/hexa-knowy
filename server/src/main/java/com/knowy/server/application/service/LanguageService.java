package com.knowy.server.application.service;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.ports.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LanguageService {

	private final CategoryRepository languageRepository;

	public LanguageService(CategoryRepository languageRepository) {
		this.languageRepository = languageRepository;
	}

	public List<Category> findAll() {
		return languageRepository.findAll();
	}
}
