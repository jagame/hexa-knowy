package com.knowy.server.repository;

import com.knowy.server.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface JpaLanguageRepository extends LanguageRepository, JpaRepository<LanguageEntity, Integer> {

	@Override
	Optional<LanguageEntity> findByName(String name);

	Set<LanguageEntity> findByNameIn(Collection<String> names);
}
