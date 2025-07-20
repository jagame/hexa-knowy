package com.knowy.server.service;

import com.knowy.server.entity.PublicUserLessonEntity;
import com.knowy.server.entity.PublicUserLessonIdEntity;
import com.knowy.server.repository.ports.PublicUserLessonRepository;
import com.knowy.server.service.exception.PublicUserLessonException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PublicUserLessonService {

	private final PublicUserLessonRepository publicUserLessonRepository;

	/**
	 * The constructor
	 *
	 * @param publicUserLessonRepository the publicUserLessonRepository
	 */
	public PublicUserLessonService(PublicUserLessonRepository publicUserLessonRepository) {
		this.publicUserLessonRepository = publicUserLessonRepository;
	}

	/**
	 * Retrieves the relationship between a public user and a lesson based on their IDs.
	 *
	 * @param userId   the ID of the user
	 * @param lessonId the ID of the lesson
	 * @return an {@link Optional} containing the found {@link PublicUserLessonEntity}, or an empty Optional if no
	 * relation exists
	 */
	public Optional<PublicUserLessonEntity> findById(int userId, int lessonId) {
		return publicUserLessonRepository.findById(new PublicUserLessonIdEntity(userId, lessonId));
	}

	// TODO - JavaDoc
	public List<PublicUserLessonEntity> findAllByCourseId(int userId, int courseId) {
		return publicUserLessonRepository.findAllByCourseId(userId, courseId);
	}

	/**
	 * Marks the current lesson as completed for the given user and, if a next lesson exists, sets its status to
	 * "in_progress".
	 *
	 * @param userId   the ID of the user
	 * @param lessonId the ID of the completed lesson
	 * @throws PublicUserLessonException if the relationship for the given user and lesson is not found
	 */
	public void updateLessonStatusToCompleted(int userId, int lessonId) throws PublicUserLessonException {
		PublicUserLessonEntity publicUserLesson = findById(userId, lessonId)
			.orElseThrow(() -> new PublicUserLessonException("Relation public user lesson not found"));
		publicUserLesson.setStatus("completed");

		Optional<PublicUserLessonEntity> publicUserLessonNext = findById(
			userId,
			publicUserLesson.getLessonEntity().getNextLesson().getId()
		);

		publicUserLessonNext.ifPresent(nextLesson ->
			nextLesson.setStatus("in_progress")
		);

		publicUserLessonRepository.save(publicUserLesson);
	}
}
