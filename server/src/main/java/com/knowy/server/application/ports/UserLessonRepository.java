package com.knowy.server.application.ports;


import com.knowy.server.domain.UserLesson;
import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;
import com.knowy.server.application.exception.data.inconsistent.notfound.KnowyUserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserLessonRepository {
	boolean existsByUserIdAndLessonId(Integer userId, Integer lessonId) throws KnowyInconsistentDataException;

	List<Integer> findCourseIdsByUserId(Integer userId) throws KnowyInconsistentDataException;

	boolean existsById(int userId, int lessonId) throws KnowyInconsistentDataException;

	UserLesson save(UserLesson userLesson) throws KnowyInconsistentDataException, KnowyUserNotFoundException;

	int countByUserIdAndCourseIdAndStatus(Integer userId, Integer courseId, UserLesson.ProgressStatus status) throws KnowyInconsistentDataException;

	Optional<UserLesson> findById(int userId, int lessonId) throws KnowyInconsistentDataException;

	List<UserLesson> findAllByCourseId(int userId, int courseId) throws KnowyInconsistentDataException;
}