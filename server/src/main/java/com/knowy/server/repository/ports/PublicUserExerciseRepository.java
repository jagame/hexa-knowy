package com.knowy.server.repository.ports;

import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.entity.PublicUserExerciseId;

import java.util.List;
import java.util.Optional;

public interface PublicUserExerciseRepository {

	<S extends PublicUserExerciseEntity> S save(S entity);

	Optional<PublicUserExerciseEntity> findById(PublicUserExerciseId id);

	List<PublicUserExerciseEntity> findAll();

	List<PublicUserExerciseEntity> findAllByPublicUserId(int publicUserId);

	Optional<PublicUserExerciseEntity> findNextExerciseByLessonId(int publicUserId, int lessonId);

	Optional<PublicUserExerciseEntity> findNextExerciseByUserId(int userId);

	Optional<Double> findAverageRateByLessonId(int lessonId);
}