package com.knowy.server.repository.adapters;

import com.knowy.server.entity.CourseEntity;
import com.knowy.server.repository.ports.CourseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaCourseRepository extends CourseRepository, JpaRepository<CourseEntity, Integer> {
	@Override
	@NonNull
	List<CourseEntity> findAll();

	@Override
	List<CourseEntity> findByIdIn(List<Integer> ids);

	@Override
	@Query("SELECT c FROM CourseEntity c ORDER BY function('RANDOM')")
	List<CourseEntity> findAllRandom();
}
