package com.knowy.server.repository.adapters;

import com.knowy.server.entity.ExerciseEntity;
import com.knowy.server.repository.ports.ExerciseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaExerciseRepository extends ExerciseRepository, JpaRepository<ExerciseEntity, Integer> {
	Optional<ExerciseEntity> findById(int id);
	List<ExerciseEntity> findByLessonId(Integer lessonId);

}
