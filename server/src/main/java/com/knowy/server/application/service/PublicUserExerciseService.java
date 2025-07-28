package com.knowy.server.application.service;

import com.knowy.server.application.domain.UserExercise;
import com.knowy.server.application.ports.ExerciseRepository;
import com.knowy.server.application.ports.PublicUserExerciseRepository;
import com.knowy.server.application.ports.UserRepository;
import com.knowy.server.application.service.exception.UserNotFoundException;
import com.knowy.server.application.service.model.ExerciseDifficult;
import com.knowy.server.util.exception.ExerciseNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class PublicUserExerciseService {

	private final PublicUserExerciseRepository publicUserExerciseRepository;
	private final UserRepository userRepository;
	private final ExerciseRepository exerciseRepository;

	/**
	 * The constructor
	 *
	 * @param publicUserExerciseRepository the publicUserExerciseRepository
	 * @param userRepository               the publicUserRepository
	 * @param exerciseRepository           the exerciseRepository
	 */
	public PublicUserExerciseService(PublicUserExerciseRepository publicUserExerciseRepository, UserRepository userRepository, ExerciseRepository exerciseRepository) {
		this.publicUserExerciseRepository = publicUserExerciseRepository;
		this.userRepository = userRepository;
		this.exerciseRepository = exerciseRepository;
	}

	/**
	 * Retrieves the next available exercise for a specific user and lesson.
	 *
	 * @param userId   the ID of the public user.
	 * @param lessonId the ID of the lesson.
	 * @return an {@code Optional} containing the next exercise if available, or empty if none is found.
	 */
	public Optional<UserExercise> findNextExerciseByLessonId(int userId, int lessonId) {
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
	public UserExercise getNextExerciseByLessonId(int userId, int lessonId) throws ExerciseNotFoundException {
		return findNextExerciseByLessonId(userId, lessonId)
			.orElseThrow(() -> new ExerciseNotFoundException("No next exercise found for user ID " + userId + " in lesson ID " + lessonId));
	}

	/**
	 * Retrieves the next available exercise for a specific user, without filtering by lesson.
	 *
	 * @param userId the ID of the public user.
	 * @return an {@code Optional} containing the next exercise if available, or empty if none is found.
	 */
	public Optional<UserExercise> findNextExerciseByUserId(int userId) {
		return publicUserExerciseRepository.findNextExerciseByUserId(userId);
	}

	/**
	 * Retrieves the next exercise available for a given user.
	 *
	 * @param userId the ID of the user
	 * @return the next PublicUserExerciseEntity for the user
	 * @throws ExerciseNotFoundException if no next exercise is found for the user
	 */
	public UserExercise getNextExerciseByUserId(int userId) throws ExerciseNotFoundException {
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
	public Optional<UserExercise> findById(int userId, int exerciseId) {
		return publicUserExerciseRepository.findById(userId, exerciseId);
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
	public UserExercise getByIdOrCreate(int userId, int exerciseId) throws UserNotFoundException, ExerciseNotFoundException {
		Optional<UserExercise> publicUserExercise = findById(userId, exerciseId);
		if (publicUserExercise.isEmpty()) {
			return createUserExerciseEntity(userId, exerciseId);
		}
		return publicUserExercise.get();
	}

	private UserExercise createUserExerciseEntity(int userId, int exerciseId)
		throws UserNotFoundException, ExerciseNotFoundException {

		UserExercise result = new UserExercise();
		result.setUserId(userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("User " + userId + " not found"))
			.id());
		result.setExerciseId(exerciseRepository.findById(exerciseId)
			.orElseThrow(() -> new ExerciseNotFoundException("Exercise " + exerciseId + " not found"))
			.id());
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
	 * @return the persisted entity.
	 * @throws NullPointerException if {@code publicUserExerciseEntity} is {@code null}.
	 */
	public UserExercise save(UserExercise userExercise) {
		Objects.requireNonNull(userExercise, "publicUserExerciseEntity cannot be null");
		return publicUserExerciseRepository.save(userExercise);
	}

	/**
	 * Updates the user's exercise record based on the difficulty of their answer.
	 *
	 * <p>This method adjusts the user's rate and schedules the next review time,
	 * according to the difficulty level provided.
	 * </p>
	 *
	 * @param exerciseDifficult the difficulty level chosen by the user for this exercise
	 * @throws NullPointerException if either parameter is null
	 */
	public void processUserAnswer(ExerciseDifficult exerciseDifficult, UserExercise userExercise) {
		difficultSelect(exerciseDifficult, userExercise);
		save(userExercise);
	}

	private void difficultSelect(ExerciseDifficult exerciseDifficult, UserExercise userExercise) {
		Objects.requireNonNull(exerciseDifficult, "exerciseDifficult cannot be null");
		Objects.requireNonNull(userExercise, "publicUserExerciseEntity cannot be null");

		switch (exerciseDifficult) {
			case EASY -> easySelect(userExercise);
			case MEDIUM -> mediumSelect(userExercise);
			case HARD -> hardSelect(userExercise);
			case FAIL -> failSelect(userExercise);
		}
	}

	private void easySelect(UserExercise userExercise) {
		userExercise.setRate(userExercise.rate() + 45);

		if (userExercise.rate() >= 90) {
			userExercise.setNextReview(LocalDateTime.now().plusDays(1));
		} else {
			userExercise.setNextReview(LocalDateTime.now().plusMinutes(15));
		}
	}

	private void mediumSelect(UserExercise userExercise) {
		userExercise.setRate(userExercise.rate() + 20);

		if (userExercise.rate() >= 90) {
			userExercise.setNextReview(LocalDateTime.now().plusDays(1));
		} else {
			userExercise.setNextReview(LocalDateTime.now().plusMinutes(10));
		}
	}

	private void hardSelect(UserExercise userExercise) {
		userExercise.setRate(userExercise.rate() - 15);
		userExercise.setNextReview(LocalDateTime.now().plusMinutes(5));
	}

	private void failSelect(UserExercise userExercise) {
		userExercise.setRate(userExercise.rate() - 30);
		userExercise.setNextReview(LocalDateTime.now().plusMinutes(1));
	}
}
