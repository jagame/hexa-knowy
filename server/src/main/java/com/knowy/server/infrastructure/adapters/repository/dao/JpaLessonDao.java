package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.application.domain.Lesson;
import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface JpaLessonDao extends JpaRepository<LessonEntity, Integer> {

	List<LessonEntity> findByCourseId(Integer courseId);

	List<LessonEntity> findByDocumentationId(int documentationId);

	int countByCourseId(Integer courseId);
}
