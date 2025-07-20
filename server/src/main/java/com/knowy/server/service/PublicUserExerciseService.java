package com.knowy.server.service;

import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.entity.PublicUserExerciseId;
import com.knowy.server.repository.ports.ExerciseRepository;
import com.knowy.server.repository.ports.PublicUserExerciseRepository;
import com.knowy.server.repository.ports.PublicUserRepository;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.service.model.ExerciseDifficult;
import com.knowy.server.util.exception.ExerciseNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublicUserExerciseService {

	private final PublicUserExerciseRepository publicUserExerciseRepository;
	private final PublicUserRepository publicUserRepository;
	private final ExerciseRepository exerciseRepository;

	/**
	 * The constructor
	 *
	 * @param publicUserExerciseRepository the publicUserExerciseRepository
	 * @param publicUserRepository         the publicUserRepository
	 * @param exerciseRepository           the exerciseRepository
	 */
	public PublicUserExerciseService(PublicUserExerciseRepository publicUserExerciseRepository, PublicUserRepository publicUserRepository, ExerciseRepository exerciseRepository) {
		this.publicUserExerciseRepository = publicUserExerciseRepository;
		this.publicUserRepository = publicUserRepository;
		this.exerciseRepository = exerciseRepository;
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

	/**
	 * Retrieves the next exercise for a given user and lesson.
	 *
	 * @param userId   the ID of the user
	 * @param lessonId the ID of the lesson
	 * @return the next PublicUserExerciseEntity for the user in the lesson
	 * @throws ExerciseNotFoundException if no next exercise is found for the user in the specified lesson
	 */
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

	/**
	 * Retrieves the next exercise available for a given user.
	 *
	 * @param userId the ID of the user
	 * @return the next PublicUserExerciseEntity for the user
	 * @throws ExerciseNotFoundException if no next exercise is found for the user
	 */
	public PublicUserExerciseEntity getNextExerciseByUserId(int userId) throws ExerciseNotFoundException {
		return findNextExerciseByUserId(userId)
			.orElseThrow(() -> new ExerciseNotFoundException("No next exercise found for user ID " + userId));
	}

	/**
	 * Finds a PublicUserExerciseEntity by user ID and exercise ID.
	 *
	 * @param userId     the ID of the user
	 * @param exerciseId the ID of the exercise
	 * @return an Optional containing the PublicUserExerciseEntity if found, otherwise empty
	 */
	public Optional<PublicUserExerciseEntity> findById(int userId, int exerciseId) {
		return publicUserExerciseRepository.findById(new PublicUserExerciseId(userId, exerciseId));
	}

	/**
	 * Retrieves the PublicUserExerciseEntity for a given user and exercise. If it does not exist, create a new one.
	 *
	 * @param userId     the ID of the user
	 * @param exerciseId the ID of the exercise
	 * @return the existing or newly created PublicUserExerciseEntity
	 * @throws UserNotFoundException     if the user is not found
	 * @throws ExerciseNotFoundException if the exercise is not found
	 */
	public PublicUserExerciseEntity getByIdOrCreate(int userId, int exerciseId) throws UserNotFoundException, ExerciseNotFoundException {
		Optional<PublicUserExerciseEntity> publicUserExercise = findById(userId, exerciseId);
		if (publicUserExercise.isEmpty()) {
			return createUserExerciseEntity(userId, exerciseId);
		}
		return publicUserExercise.get();
	}

	private PublicUserExerciseEntity createUserExerciseEntity(int userId, int exerciseId) throws UserNotFoundException, ExerciseNotFoundException {
		var result = new PublicUserExerciseEntity(userId, exerciseId);
		result.setPublicUserEntity(publicUserRepository.findUserById(userId)
			.orElseThrow(() -> new UserNotFoundException("User " + userId + " not found")));
		result.setExerciseEntity(exerciseRepository.findById(exerciseId)
			.orElseThrow(() -> new ExerciseNotFoundException("Exercise " + exerciseId + " not found")));
		return result;
	}

	/**
	 * Finds the average rate (score) for all exercises in a given lesson.
	 *
	 * @param lessonId the ID of the lesson
	 * @return an Optional containing the average rate, or empty if none found
	 */
	public Optional<Double> findAverageRateByLessonId(int lessonId) {
		return publicUserExerciseRepository.findAverageRateByLessonId(lessonId);
	}

	/**
	 * Gets the average rate (score) for all exercises in a given lesson. Throws an exception if no average rate is
	 * found.
	 *
	 * @param lessonId the ID of the lesson
	 * @return the average rate for the lesson
	 * @throws ExerciseNotFoundException if no average rate is found for the lesson
	 */
	public double getAverageRateByLessonId(int lessonId) throws ExerciseNotFoundException {
		return findAverageRateByLessonId(lessonId)
			.orElseThrow(() -> new ExerciseNotFoundException(
				"No average rate found for lesson ID " + lessonId));
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

	/**
	 * Updates the user's exercise record based on the difficulty of their answer.
	 *
	 * <p>This method adjusts the user's rate and schedules the next review time,
	 * according to the difficulty level provided.
	 * </p>
	 *
	 * @param exerciseDifficult the difficulty level chosen by the user for this exercise
	 * @param exerciseEntity    the user's current exercise entity to update
	 * @throws NullPointerException if either parameter is null
	 */
	public void processUserAnswer(ExerciseDifficult exerciseDifficult, PublicUserExerciseEntity exerciseEntity) {
		difficultSelect(exerciseDifficult, exerciseEntity);
		save(exerciseEntity);
	}

	private void difficultSelect(ExerciseDifficult exerciseDifficult, PublicUserExerciseEntity publicUserExerciseEntity) {
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
