package com.knowy.server.repository.adapters;

import com.knowy.server.entity.ExerciseEntity;
import com.knowy.server.repository.ports.ExerciseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaExerciseRepository extends ExerciseRepository, JpaRepository<ExerciseEntity, Integer> {
	Optional<ExerciseEntity> findById(int id);
}
