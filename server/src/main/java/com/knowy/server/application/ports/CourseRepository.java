package com.knowy.server.application.ports;

import com.knowy.server.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseRepository {
	List<Course> findByIdIn(List<Integer> ids);

	List<Course> findAll();

	Optional<Course> findById(Integer id);

	List<Course> findAllRandom();
}
