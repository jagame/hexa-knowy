package com.knowy.server.repository;

import com.knowy.server.entity.LanguageEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LanguageRepository {

	Optional<LanguageEntity> findByName(String name);

	Set<LanguageEntity> findByNameIn(Set<String> names);

	Set<LanguageEntity> findByNameInIgnoreCase(String[] names);
}
