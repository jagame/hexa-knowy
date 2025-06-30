package com.knowy.server.repository;

import com.knowy.server.entity.LanguageEntity;

import java.util.List;
import java.util.Optional;

public interface LanguageRepository {

	Optional<LanguageEntity> findByName(String name);
}
