package com.knowy.server.repository.adapters;

import com.knowy.server.entity.LessonEntity;
import com.knowy.server.repository.ports.LessonRepository;
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
