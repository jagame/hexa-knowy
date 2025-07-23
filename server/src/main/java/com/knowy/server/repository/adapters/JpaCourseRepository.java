package com.knowy.server.repository.adapters;

import com.knowy.server.entity.CourseEntity;
import com.knowy.server.repository.ports.CourseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCourseRepository extends CourseRepository, JpaRepository<CourseEntity, Integer>{
	@Override
	@NonNull
	List<CourseEntity> findAll();

	@Override
	List<CourseEntity> findByIdIn(List<Integer> ids);

	@Query("SELECT DISTINCT c FROM CourseEntity c " +
		"JOIN LessonEntity l ON l.course.id = c.id " +
		"JOIN PublicUserLessonEntity pul ON pul.lessonId = l.id " +
		"WHERE pul.userId = :userId")
	Page<CourseEntity> findCoursesByUserId(@Param("userId") Integer userId, Pageable pageable);

}
