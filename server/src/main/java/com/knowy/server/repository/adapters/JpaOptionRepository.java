package com.knowy.server.repository.adapters;

import com.knowy.server.entity.OptionEntity;
import com.knowy.server.repository.ports.OptionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOptionRepository extends OptionRepository, JpaRepository<OptionEntity, Integer> {
}
