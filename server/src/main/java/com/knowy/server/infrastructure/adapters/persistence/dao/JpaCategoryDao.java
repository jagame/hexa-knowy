package com.knowy.server.infrastructure.adapters.persistence.dao;

import com.knowy.server.infrastructure.adapters.persistence.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface JpaCategoryDao extends JpaRepository<CategoryEntity, Integer> {
	@Query(value = "SELECT id, name FROM language l WHERE l.name ILIKE ANY (ARRAY[:names])", nativeQuery = true)
	Set<CategoryEntity> findByNameInIgnoreCase(@Param("names") String[] names);
}
