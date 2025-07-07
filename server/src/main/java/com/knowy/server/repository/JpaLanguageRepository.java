package com.knowy.server.repository;

import com.knowy.server.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLanguageRepository extends LanguageRepository, JpaRepository<LanguageEntity, Integer> {

}
