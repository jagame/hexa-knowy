package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.Exercise;
import com.knowy.server.infrastructure.adapters.repository.entity.ExerciseEntity;

public class JpaExerciseMapper implements EntityMapper<Exercise, ExerciseEntity> {

	private final JpaOptionMapper jpaOptionMapper;

	public JpaExerciseMapper(JpaOptionMapper jpaOptionMapper) {
		this.jpaOptionMapper = jpaOptionMapper;
	}

	@Override
	public Exercise toDomain(ExerciseEntity entity) {
		return new Exercise(
			entity.getId(),
			entity.getLesson().getId(),
			entity.getQuestion(),
			entity.getOptions().stream()
				.map(jpaOptionMapper::toDomain)
				.toList()
		);
	}

	@Override
	public ExerciseEntity toEntity(Exercise domain) {
		return null;
	}
}
