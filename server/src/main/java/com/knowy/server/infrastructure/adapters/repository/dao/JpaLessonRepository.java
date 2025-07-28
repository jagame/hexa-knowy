package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;
import com.knowy.server.application.ports.LessonRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLessonRepository extends JpaRepository<LessonEntity,Integer> {
	List<LessonEntity> findByCourseId(Integer courseId);

	int countByCourseId(Integer courseId);
}
