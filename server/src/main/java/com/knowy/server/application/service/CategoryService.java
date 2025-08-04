package com.knowy.server.application.service;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.ports.CategoryRepository;

import java.util.List;

public class CategoryService {

	private final CategoryRepository categoryRepository;

	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}
}
