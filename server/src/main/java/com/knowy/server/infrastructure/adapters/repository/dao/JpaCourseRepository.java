package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCourseRepository extends JpaRepository<CourseEntity, Integer> {
	@NonNull
	List<CourseEntity> findAll();

	List<CourseEntity> findByIdIn(List<Integer> ids);

	@Query("SELECT c FROM CourseEntity c ORDER BY function('RANDOM')")
	List<CourseEntity> findAllRandom();
}
