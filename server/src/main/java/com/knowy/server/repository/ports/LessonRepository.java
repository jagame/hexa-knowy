package com.knowy.server.repository.ports;

import com.knowy.server.entity.LessonEntity;

import java.util.List;


public interface LessonRepository {

	List<LessonEntity> findByCourseId(Integer courseId);
	int countByCourseId(Integer courseId);
}
