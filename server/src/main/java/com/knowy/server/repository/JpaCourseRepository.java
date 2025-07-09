package com.knowy.server.repository;

import com.knowy.server.entity.CourseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
