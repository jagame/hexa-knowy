package com.knowy.server.repository.ports;

import com.knowy.server.entity.ExerciseEntity;
import com.knowy.server.entity.OptionEntity;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository {
	Optional<ExerciseEntity> findById(int id);
	List<ExerciseEntity> findByLessonId(Integer lessonId);
}
