package com.knowy.server.repository;

import com.knowy.server.entity.LessonEntity;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface LessonRepository {

	List<LessonEntity> findByCourse_Id(Integer courseId);
}
