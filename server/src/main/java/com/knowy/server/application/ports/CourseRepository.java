package com.knowy.server.application.ports;

import com.knowy.server.infrastructure.adapters.repository.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
	List<CourseEntity> findByIdIn(List<Integer> ids);

	List<CourseEntity> findAll();

	Optional<CourseEntity> findById(Integer id);

	List<CourseEntity> findAllRandom();
}
