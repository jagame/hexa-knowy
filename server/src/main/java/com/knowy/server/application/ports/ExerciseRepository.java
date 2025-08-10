package com.knowy.server.application.ports;

import com.knowy.server.domain.Exercise;

import java.util.Optional;

public interface ExerciseRepository {
	Optional<Exercise> findById(int id);
}
