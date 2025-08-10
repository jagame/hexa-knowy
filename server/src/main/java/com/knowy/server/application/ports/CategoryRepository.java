package com.knowy.server.application.ports;

import com.knowy.server.domain.Category;

import java.util.List;
import java.util.Set;

public interface CategoryRepository {
	Set<Category> findByNameInIgnoreCase(String[] names);

	List<Category> findAll();
}
