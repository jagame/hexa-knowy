package com.knowy.server.application;

import com.knowy.server.domain.UserExercise;
import com.knowy.server.application.exception.data.KnowyDataAccessException;
import com.knowy.server.application.exception.data.inconsistent.notfound.KnowyExerciseNotFoundException;
import com.knowy.server.application.ports.ExerciseRepository;
import com.knowy.server.application.ports.UserExerciseRepository;
import com.knowy.server.application.ports.UserRepository;
import com.knowy.server.application.exception.data.inconsistent.notfound.KnowyUserNotFoundException;
import com.knowy.server.domain.ExerciseDifficult;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class UserExerciseService {

	private final UserExerciseRepository userExerciseRepository;
	private final UserRepository userRepository;
	private final ExerciseRepository exerciseRepository;

	/**
	 * The constructor
	 *
	 * @param userExerciseRepository the publicUserExerciseRepository
	 * @param userRepository         the publicUserRepository
	 * @param exerciseRepository     the exerciseRepository
	 */
	public UserExerciseService(UserExerciseRepository userExerciseRepository, UserRepository userRepository, ExerciseRepository exerciseRepository) {
		this.userExerciseRepository = userExerciseRepository;
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
	public Optional<UserExercise> findNextExerciseByLessonId(int userId, int lessonId) throws KnowyDataAccessException {
		return userExerciseRepository.findNextExerciseByLessonId(userId, lessonId);
	}

	/**
	 * Retrieves the next exercise for a given user and lesson.
	 *
	 * @param userId   the ID of the user
	 * @param lessonId the ID of the lesson
	 * @return the next PublicUserExerciseEntity for the user in the lesson
	 * @throws KnowyExerciseNotFoundException if no next exercise is found for the user in the specified lesson
	 */
	public UserExercise getNextExerciseByLessonId(int userId, int lessonId) throws KnowyExerciseNotFoundException, KnowyDataAccessException {
		return findNextExerciseByLessonId(userId, lessonId)
			.orElseThrow(() -> new KnowyExerciseNotFoundException("No next exercise found for user ID " + userId + " in lesson ID " + lessonId));
	}

	/**
	 * Retrieves the next available exercise for a specific user, without filtering by lesson.
	 *
	 * @param userId the ID of the public user.
	 * @return an {@code Optional} containing the next exercise if available, or empty if none is found.
	 */
	public Optional<UserExercise> findNextExerciseByUserId(int userId) throws KnowyDataAccessException {
		return userExerciseRepository.findNextExerciseByUserId(userId);
	}

	/**
	 * Retrieves the next exercise available for a given user.
	 *
	 * @param userId the ID of the user
	 * @return the next PublicUserExerciseEntity for the user
	 * @throws KnowyExerciseNotFoundException if no next exercise is found for the user
	 */
	public UserExercise getNextExerciseByUserId(int userId) throws KnowyExerciseNotFoundException, KnowyDataAccessException {
		return findNextExerciseByUserId(userId)
			.orElseThrow(() -> new KnowyExerciseNotFoundException("No next exercise found for user ID " + userId));
	}

	/**
	 * Finds a PublicUserExerciseEntity by user ID and exercise ID.
	 *
	 * @param userId     the ID of the user
	 * @param exerciseId the ID of the exercise
	 * @return an Optional containing the PublicUserExerciseEntity if found, otherwise empty
	 */
	public Optional<UserExercise> findById(int userId, int exerciseId) throws KnowyDataAccessException {
		return userExerciseRepository.findById(userId, exerciseId);
	}

	/**
	 * Retrieves the PublicUserExerciseEntity for a given user and exercise. If it does not exist, create a new one.
	 *
	 * @param userId     the ID of the user
	 * @param exerciseId the ID of the exercise
	 * @return the existing or newly created PublicUserExerciseEntity
	 * @throws KnowyUserNotFoundException     if the user is not found
	 * @throws KnowyExerciseNotFoundException if the exercise is not found
	 */
	public UserExercise getByIdOrCreate(int userId, int exerciseId)
		throws KnowyUserNotFoundException, KnowyExerciseNotFoundException, KnowyDataAccessException {

		Optional<UserExercise> publicUserExercise = findById(userId, exerciseId);
		if (publicUserExercise.isEmpty()) {
			return createUserExerciseEntity(userId, exerciseId);
		}
		return publicUserExercise.get();
	}

	private UserExercise createUserExerciseEntity(int userId, int exerciseId)
		throws KnowyUserNotFoundException, KnowyExerciseNotFoundException {

		return new UserExercise(
			userRepository.findById(userId)
				.orElseThrow(() -> new KnowyUserNotFoundException("User " + userId + " not found")),
			exerciseRepository.findById(exerciseId)
				.orElseThrow(() -> new KnowyExerciseNotFoundException("Exercise " + exerciseId + " not found")),
			0,
			LocalDateTime.now()
		);
	}

	/**
	 * Finds the average rate (score) for all exercises in a given lesson.
	 *
	 * @param lessonId the ID of the lesson
	 * @return an Optional containing the average rate, or empty if none found
	 */
	public Optional<Double> findAverageRateByLessonId(int lessonId) throws KnowyDataAccessException {
		return userExerciseRepository.findAverageRateByLessonId(lessonId);
	}

	/**
	 * Gets the average rate (score) for all exercises in a given lesson. Throws an exception if no average rate is
	 * found.
	 *
	 * @param lessonId the ID of the lesson
	 * @return the average rate for the lesson
	 * @throws KnowyExerciseNotFoundException if no average rate is found for the lesson
	 */
	public double getAverageRateByLessonId(int lessonId) throws KnowyExerciseNotFoundException, KnowyDataAccessException {
		return findAverageRateByLessonId(lessonId)
			.orElseThrow(() -> new KnowyExerciseNotFoundException(
				"No average rate found for lesson ID " + lessonId));
	}

	/**
	 * Saves or updates a public user exercise entity in the repository.
	 *
	 * @return the persisted entity.
	 * @throws NullPointerException if {@code publicUserExerciseEntity} is {@code null}.
	 */
	public UserExercise save(UserExercise userExercise) throws KnowyDataAccessException, KnowyUserNotFoundException, KnowyExerciseNotFoundException {
		Objects.requireNonNull(userExercise, "publicUserExerciseEntity cannot be null");
		return userExerciseRepository.save(userExercise);
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
	public void processUserAnswer(ExerciseDifficult exerciseDifficult, UserExercise userExercise) throws KnowyDataAccessException, KnowyExerciseNotFoundException, KnowyUserNotFoundException {
		UserExercise updatedUserExercise = difficultSelect(exerciseDifficult, userExercise);
		save(updatedUserExercise);
	}

	private UserExercise difficultSelect(ExerciseDifficult exerciseDifficult, UserExercise userExercise) {
		Objects.requireNonNull(exerciseDifficult, "exerciseDifficult cannot be null");
		Objects.requireNonNull(userExercise, "publicUserExerciseEntity cannot be null");

		return switch (exerciseDifficult) {
			case EASY -> easySelect(userExercise);
			case MEDIUM -> mediumSelect(userExercise);
			case HARD -> hardSelect(userExercise);
			case FAIL -> failSelect(userExercise);
		};
	}

	private UserExercise easySelect(UserExercise userExercise) {
		int updatedRate = userExercise.rate() + 45;

		LocalDateTime updatedNextReview = LocalDateTime.now()
			.plus(userExercise.rate() >= 90 ? Duration.ofDays(1) : Duration.ofMinutes(15));

		return new UserExercise(
			userExercise.user(),
			userExercise.exercise(),
			updatedRate,
			updatedNextReview
		);
	}

	private UserExercise mediumSelect(UserExercise userExercise) {
		int updatedRate = userExercise.rate() + 20;

		LocalDateTime updatedNextReview = LocalDateTime.now()
			.plus(userExercise.rate() >= 90 ? Duration.ofDays(1) : Duration.ofMinutes(7));

		return new UserExercise(
			userExercise.user(),
			userExercise.exercise(),
			updatedRate,
			updatedNextReview
		);
	}

	private UserExercise hardSelect(UserExercise userExercise) {
		int updatedRate = userExercise.rate() - 15;
		LocalDateTime updatedNextReview = LocalDateTime.now().plusMinutes(5);

		return new UserExercise(
			userExercise.user(),
			userExercise.exercise(),
			updatedRate,
			updatedNextReview
		);
	}

	private UserExercise failSelect(UserExercise userExercise) {
		int updatedRate = userExercise.rate() - 30;
		LocalDateTime updatedNextReview = LocalDateTime.now().plusMinutes(1);

		return new UserExercise(
			userExercise.user(),
			userExercise.exercise(),
			updatedRate,
			updatedNextReview
		);
	}
}
