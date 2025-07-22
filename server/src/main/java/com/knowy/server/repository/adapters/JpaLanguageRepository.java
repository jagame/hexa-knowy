package com.knowy.server.repository.adapters;

import com.knowy.server.entity.LanguageEntity;
import com.knowy.server.repository.ports.LanguageRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JpaLanguageRepository extends LanguageRepository, JpaRepository<LanguageEntity, Integer> {
	@Query(value = "SELECT id, name FROM language l WHERE l.name ILIKE ANY (ARRAY[:names])", nativeQuery = true)
	Set<LanguageEntity> findByNameInIgnoreCase(@Param("names") String[] names);

	@Override
	@NonNull
	List<LanguageEntity> findAll();
}
