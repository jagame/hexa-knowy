package com.knowy.server.application.ports;

import com.knowy.server.application.domain.UserExercise;
import com.knowy.server.application.exception.KnowyDataAccessException;
import com.knowy.server.application.exception.KnowyExerciseNotFoundException;
import com.knowy.server.application.exception.KnowyUserNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserExerciseRepository {

	UserExercise save(UserExercise userExercise) throws KnowyDataAccessException, KnowyExerciseNotFoundException, KnowyUserNotFoundException;

	Optional<UserExercise> findById(int userId, int exerciseId) throws KnowyDataAccessException;

	List<UserExercise> findAll() throws KnowyDataAccessException;

	Optional<UserExercise> findNextExerciseByLessonId(int publicUserId, int lessonId) throws KnowyDataAccessException;

	Optional<UserExercise> findNextExerciseByUserId(int userId) throws KnowyDataAccessException;

	Optional<Double> findAverageRateByLessonId(int lessonId) throws KnowyDataAccessException;
}