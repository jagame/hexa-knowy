package com.knowy.server.repository.adapters;

import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.entity.PublicUserExerciseId;
import com.knowy.server.repository.ports.PublicUserExerciseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JpaPublicUserExerciseRepository extends PublicUserExerciseRepository,
	JpaRepository<PublicUserExerciseEntity, PublicUserExerciseId> {

	@Override
	@NonNull
	Optional<PublicUserExerciseEntity> findById(@NonNull PublicUserExerciseId id);

	@Override
	@NonNull
	List<PublicUserExerciseEntity> findAll();

	@Override
	@Query("SELECT p FROM PublicUserExerciseEntity p WHERE p.id = :id")
	List<PublicUserExerciseEntity> findAllByPublicUserId(int publicUserId);
}
