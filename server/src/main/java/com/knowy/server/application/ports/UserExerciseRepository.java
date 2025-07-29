package com.knowy.server.application.ports;

import com.knowy.server.application.domain.UserExercise;

import java.util.List;
import java.util.Optional;

public interface UserExerciseRepository {

	UserExercise save(UserExercise entity);

	Optional<UserExercise> findById(int userId, int exerciseId);

	List<UserExercise> findAll();

	Optional<UserExercise> findNextExerciseByLessonId(int publicUserId, int lessonId);

	Optional<UserExercise> findNextExerciseByUserId(int userId);

	Optional<Double> findAverageRateByLessonId(int lessonId);
}