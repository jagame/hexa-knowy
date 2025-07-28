package com.knowy.server.infrastructure.adapters.repository.dao;

import com.knowy.server.infrastructure.adapters.repository.entity.ExerciseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaExerciseRepository extends JpaRepository<ExerciseEntity, Integer> {
	Optional<ExerciseEntity> findById(int id);
}
