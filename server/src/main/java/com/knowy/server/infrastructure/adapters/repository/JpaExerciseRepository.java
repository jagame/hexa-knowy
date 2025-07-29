package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.Exercise;
import com.knowy.server.application.ports.ExerciseRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaExerciseDao;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaExerciseMapper;

import java.util.Optional;

public class JpaExerciseRepository implements ExerciseRepository {

	private final JpaExerciseDao jpaExerciseDao;
	private final JpaExerciseMapper jpaExerciseMapper;

	public JpaExerciseRepository(JpaExerciseDao jpaExerciseDao, JpaExerciseMapper jpaExerciseMapper) {
		this.jpaExerciseDao = jpaExerciseDao;
		this.jpaExerciseMapper = jpaExerciseMapper;
	}

	@Override
	public Optional<Exercise> findById(int id) {
		return jpaExerciseDao.findById(id).map(jpaExerciseMapper::toDomain);
	}
}
