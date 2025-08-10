package com.knowy.server.infrastructure.adapters.persistence;

import com.knowy.server.domain.Exercise;
import com.knowy.server.application.ports.ExerciseRepository;
import com.knowy.server.infrastructure.adapters.persistence.dao.JpaExerciseDao;
import com.knowy.server.infrastructure.adapters.persistence.mapper.JpaExerciseMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
