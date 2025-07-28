package com.knowy.server.application.ports;


import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonIdEntity;

import java.util.List;
import java.util.Optional;

public interface PublicUserLessonRepository {
	boolean existsByUserIdAndLessonId(Integer userId, Integer lessonId);

	List<Integer> findCourseIdsByUserId(Integer userId);

	boolean existsById(PublicUserLessonIdEntity id);

	<S extends PublicUserLessonEntity> S save(S entity);

	int countByUserIdAndCourseIdAndStatus(Integer userId, Integer courseId, String status);

	Optional<PublicUserLessonEntity> findById(PublicUserLessonIdEntity publicUserLessonIdEntity);

	List<PublicUserLessonEntity> findAllByCourseId(int userId, int courseId);
}
