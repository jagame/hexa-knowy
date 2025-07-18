package com.knowy.server.repository.ports;


import com.knowy.server.entity.PublicUserLessonEntity;
import com.knowy.server.entity.PublicUserLessonIdEntity;

import java.util.List;
import java.util.Optional;

public interface PublicUserLessonRepository {
	boolean existsByUserIdAndLessonId(Integer userId, Integer lessonId);

	List<Integer> findCourseIdsByUserId(Integer userId);

	boolean existsById(PublicUserLessonIdEntity id);

	<S extends PublicUserLessonEntity> S save(S entity);

	int countByUserIdAndCourseIdAndStatus(Integer userId, Integer courseId, String status);

	Optional<PublicUserLessonEntity> findById(PublicUserLessonIdEntity id);
}
