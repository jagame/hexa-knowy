package com.knowy.server.repository.ports;

import com.knowy.server.entity.ExerciseEntity;

import java.util.Optional;

public interface ExerciseRepository {
	Optional<ExerciseEntity> findById(int id);
}
