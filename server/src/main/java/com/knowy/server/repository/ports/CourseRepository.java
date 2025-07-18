package com.knowy.server.repository.ports;

import com.knowy.server.entity.CourseEntity;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
	List<CourseEntity> findByIdIn(List<Integer> ids);
	List<CourseEntity> findAll();
	Optional<CourseEntity> findById(Integer id);
}
