package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.Category;
import com.knowy.server.infrastructure.adapters.repository.entity.CategoryEntity;
import org.springframework.stereotype.Component;

@Component
public class JpaCategoryMapper implements EntityMapper<Category, CategoryEntity> {

	@Override
	public Category toDomain(CategoryEntity entity) {
		Category category = new Category();
		category.setId(entity.getId());
		category.setName(entity.getName());
		return category;
	}

	@Override
	public CategoryEntity toEntity(Category domain) {
		CategoryEntity categoryEntity = new CategoryEntity();
		categoryEntity.setId(domain.id());
		categoryEntity.setName(domain.name());
		return categoryEntity;
	}
}
