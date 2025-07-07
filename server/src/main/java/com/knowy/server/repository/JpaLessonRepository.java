package com.knowy.server.repository;

import com.knowy.server.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLessonRepository extends LessonRepository, JpaRepository<LessonEntity,Integer> {
	@Override
	List<LessonEntity> findByCourseId(Integer courseId);

	@Override
	int countByCourseId(Integer courseId);
}
