package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.ports.CategoryRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaCategoryDao;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaCategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JpaCategoryRepository implements CategoryRepository {

	private final JpaCategoryDao jpaCategoryDao;
	private final JpaCategoryMapper jpaCategoryMapper;

	public JpaCategoryRepository(JpaCategoryDao jpaCategoryDao, JpaCategoryMapper jpaCategoryMapper) {
		this.jpaCategoryDao = jpaCategoryDao;
		this.jpaCategoryMapper = jpaCategoryMapper;
	}

	@Override
	public Set<Category> findByNameInIgnoreCase(String[] names) {
		return jpaCategoryDao.findByNameInIgnoreCase(names)
			.stream()
			.map(jpaCategoryMapper::toDomain)
			.collect(Collectors.toSet());
	}

	@Override
	public List<Category> findAll() {
		return jpaCategoryDao.findAll()
			.stream()
			.map(jpaCategoryMapper::toDomain)
			.toList();
	}
}
