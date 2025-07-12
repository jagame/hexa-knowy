package com.knowy.server.repository.ports;

import com.knowy.server.entity.LanguageEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LanguageRepository {

	Optional<LanguageEntity> findByName(String name);

	Set<LanguageEntity> findByNameIn(Set<String> names);

	Set<LanguageEntity> findByNameInIgnoreCase(String[] names);

	List<LanguageEntity> findAll();
}
