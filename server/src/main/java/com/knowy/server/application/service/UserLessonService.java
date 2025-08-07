package com.knowy.server.application.service;

import com.knowy.server.application.domain.Lesson;
import com.knowy.server.application.domain.User;
import com.knowy.server.application.domain.UserLesson;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.ports.LessonRepository;
import com.knowy.server.application.ports.UserLessonRepository;
import com.knowy.server.application.service.exception.KnowyLessonNotFoundException;
import com.knowy.server.application.service.exception.KnowyUserLessonNotFoundException;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonEntity;

import java.util.List;
import java.util.Optional;

public class UserLessonService {

	private final UserLessonRepository userLessonRepository;
	private final LessonRepository lessonRepository;

	/**
	 * The constructor
	 *
	 * @param userLessonRepository the publicUserLessonRepository
	 */
	public UserLessonService(UserLessonRepository userLessonRepository, LessonRepository lessonRepository) {
		this.userLessonRepository = userLessonRepository;
		this.lessonRepository = lessonRepository;
	}

	/**
	 * Retrieves the relationship between a public user and a lesson based on their IDs.
	 *
	 * @param userId   the ID of the user
	 * @param lessonId the ID of the lesson
	 * @return an {@link Optional} containing the found {@link PublicUserLessonEntity}, or an empty Optional if no
	 * relation exists
	 */
	public Optional<UserLesson> findById(int userId, int lessonId) throws KnowyInconsistentDataException {
		return userLessonRepository.findById(userId, lessonId);
	}

	/**
	 * Retrieves all {@link PublicUserLessonEntity} records for a given user and course.
	 *
	 * <p>This method returns the user's progress across all lessons within the specified course.</p>
	 *
	 * @param userId   The ID of the user.
	 * @param courseId The ID, of course.
	 * @return A list of {@link PublicUserLessonEntity} representing the user's lesson data for the course.
	 */
	public List<UserLesson> findAllByCourseId(int userId, int courseId) throws KnowyInconsistentDataException {
		return userLessonRepository.findAllByCourseId(userId, courseId);
	}

	/**
	 * Marks the current lesson as completed for the given user and, if a next lesson exists, sets its status to
	 * "in_progress".
	 *
	 * @throws KnowyUserLessonNotFoundException if the relationship for the given user and lesson is not found
	 */
	public void updateLessonStatusToCompleted(User user, Lesson lesson)
		throws KnowyUserLessonNotFoundException, KnowyInconsistentDataException, KnowyLessonNotFoundException {
		UserLesson userLesson = findById(user.id(), lesson.id())
			.orElseThrow(() -> new KnowyUserLessonNotFoundException("Relation public user lesson not found"));

		updateNextLessonStatusToInProgress(user.id(), lesson.id());
		userLessonRepository.save(new UserLesson(
			user,
			lesson,
			userLesson.startDate(),
			UserLesson.ProgressStatus.COMPLETED
		));
	}

	private void updateNextLessonStatusToInProgress(int userId, int lessonId) throws KnowyInconsistentDataException, KnowyLessonNotFoundException, KnowyUserLessonNotFoundException {
		Lesson lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new KnowyLessonNotFoundException("Not found lesson with Id: " + lessonId));
		if (lesson.nextLessonId() == null) {
			return;
		}

		UserLesson userLesson = userLessonRepository.findById(userId, lesson.nextLessonId())
			.orElseThrow(() -> new KnowyUserLessonNotFoundException(
				"Not found lesson with id: " + lesson.nextLessonId() + "by user with id: " + userId
			));
		userLessonRepository.save(new UserLesson(
			userLesson.user(),
			userLesson.lesson(),
			userLesson.startDate(),
			UserLesson.ProgressStatus.IN_PROGRESS
		));
	}
}
