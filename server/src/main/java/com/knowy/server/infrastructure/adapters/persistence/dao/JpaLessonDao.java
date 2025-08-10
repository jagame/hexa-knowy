package com.knowy.server.infrastructure.adapters.persistence.dao;

import com.knowy.server.infrastructure.adapters.persistence.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaLessonDao extends JpaRepository<LessonEntity, Integer> {

	List<LessonEntity> findByCourseId(Integer courseId);

	@Query(value = """
		SELECT
		    l.id,
		    l.id_course,
		    l.id_next_lesson,
		    l.title,
		    l.explanation
		FROM documentation d
		    INNER JOIN lesson_documentation ld
		        ON ld.id_documentation = d.id
		    INNER JOIN lesson l
		        ON l.id = ld.id_lesson
		WHERE d.id = :id
		GROUP BY l.id
		""", nativeQuery = true)
	List<LessonEntity> findAllByDocumentationId(@Param("id") int documentationId);

	int countByCourseId(Integer courseId);
}
