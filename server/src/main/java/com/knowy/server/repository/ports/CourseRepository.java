package com.knowy.server.repository.ports;

import com.knowy.server.entity.CourseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CourseRepository {
	List<CourseEntity> findByIdIn(List<Integer> ids);
	List<CourseEntity> findAll();

	Page<CourseEntity> findCoursesByUserId(Integer userId, Pageable pageable);
}
