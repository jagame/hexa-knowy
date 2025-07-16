package com.knowy.server.service;

import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.entity.PublicUserExerciseId;
import com.knowy.server.repository.ports.PublicUserExerciseRepository;
import com.knowy.server.service.model.ExerciseDifficult;
import com.knowy.server.util.exception.ExerciseNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublicUserExerciseService {

	private final PublicUserExerciseRepository publicUserExerciseRepository;

	/**
	 * The constructor
	 *
	 * @param publicUserExerciseRepository the publicUserExerciseRepository
	 */
	public PublicUserExerciseService(PublicUserExerciseRepository publicUserExerciseRepository) {
		this.publicUserExerciseRepository = publicUserExerciseRepository;
	}

	/**
	 * Retrieves the next available exercise for a specific user and lesson.
	 *
	 * @param userId   the ID of the public user.
	 * @param lessonId the ID of the lesson.
	 * @return an {@code Optional} containing the next exercise if available, or empty if none is found.
	 */
	public Optional<PublicUserExerciseEntity> findNextExerciseByLessonId(int userId, int lessonId) {
		return publicUserExerciseRepository.findNextExerciseByLessonId(userId, lessonId);
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity getNextExerciseByLessonId(int userId, int lessonId) throws ExerciseNotFoundException {
		return findNextExerciseByLessonId(userId, lessonId)
			.orElseThrow(() -> new ExerciseNotFoundException("No next exercise found for user ID " + userId + " in lesson ID " + lessonId));
	}

	/**
	 * Retrieves the next available exercise for a specific user, without filtering by lesson.
	 *
	 * @param userId the ID of the public user.
	 * @return an {@code Optional} containing the next exercise if available, or empty if none is found.
	 */
	public Optional<PublicUserExerciseEntity> findNextExerciseByUserId(int userId) {
		return publicUserExerciseRepository.findNextExerciseByUserId(userId);
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity getNextExerciseByUserId(int userId) throws ExerciseNotFoundException {
		return findNextExerciseByUserId(userId)
			.orElseThrow(() -> new ExerciseNotFoundException("No next exercise found for user ID " + userId));
	}

	// TODO - JavaDoc
	public Optional<PublicUserExerciseEntity> findById(int userId, int exerciseId) {
		return publicUserExerciseRepository.findById(new PublicUserExerciseId(userId, exerciseId));
	}

	// TODO - JavaDoc
	public PublicUserExerciseEntity getById(int userId, int exerciseId) throws ExerciseNotFoundException {
		return findById(userId, exerciseId)
			.orElseThrow(() -> new ExerciseNotFoundException("Exercise ID " + exerciseId + " not found for user ID " + userId));
	}

	/**
	 * Saves or updates a public user exercise entity in the repository.
	 *
	 * @param publicUserExerciseEntity the entity to be saved.
	 * @return the persisted entity.
	 * @throws NullPointerException if {@code publicUserExerciseEntity} is {@code null}.
	 */
	public PublicUserExerciseEntity save(PublicUserExerciseEntity publicUserExerciseEntity) {
		Objects.requireNonNull(publicUserExerciseEntity, "publicUserExerciseEntity cannot be null");
		return publicUserExerciseRepository.save(publicUserExerciseEntity);
	}

	// TODO - JavaDoc And Finish it
	public void difficultSelect(ExerciseDifficult exerciseDifficult, PublicUserExerciseEntity publicUserExerciseEntity) {
		Objects.requireNonNull(exerciseDifficult, "exerciseDifficult cannot be null");
		Objects.requireNonNull(publicUserExerciseEntity, "publicUserExerciseEntity cannot be null");

		switch (exerciseDifficult) {
			case EASY -> easySelect(publicUserExerciseEntity);
			case MEDIUM -> mediumSelect(publicUserExerciseEntity);
			case HARD -> hardSelect(publicUserExerciseEntity);
			case FAIL -> failSelect(publicUserExerciseEntity);
		}
	}

	private void easySelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() + 45);

		if (publicUserExerciseEntity.getRate() >= 90) {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusDays(1));
		} else {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(15));
		}
	}

	private void mediumSelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() + 20);

		if (publicUserExerciseEntity.getRate() >= 90) {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusDays(1));
		} else {
			publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(10));
		}
	}

	private void hardSelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() - 15);
		publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(5));
	}

	private void failSelect(PublicUserExerciseEntity publicUserExerciseEntity) {
		publicUserExerciseEntity.setRate(publicUserExerciseEntity.getRate() - 30);
		publicUserExerciseEntity.setNextReview(LocalDateTime.now().plusMinutes(1));
	}
}
