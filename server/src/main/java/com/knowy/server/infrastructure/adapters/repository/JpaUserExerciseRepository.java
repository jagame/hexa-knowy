package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.UserExercise;
import com.knowy.server.application.ports.UserExerciseRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaExerciseDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserExerciseDao;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserExerciseEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserExerciseId;
import com.knowy.server.infrastructure.adapters.repository.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class JpaUserExerciseRepository implements UserExerciseRepository {

	private final JpaUserDao jpaUserDao;
	private final JpaExerciseDao jpaExerciseDao;
	private final JpaUserExerciseDao jpaUserExerciseDao;
	private final JpaUserExerciseMapper jpaUserExerciseMapper;

	public JpaUserExerciseRepository(JpaUserDao jpaUserDao, JpaExerciseDao jpaExerciseDao, JpaUserExerciseDao jpaUserExerciseDao) {
		this.jpaUserDao = jpaUserDao;
		this.jpaExerciseDao = jpaExerciseDao;
		this.jpaUserExerciseDao = jpaUserExerciseDao;
		this.jpaUserExerciseMapper = new JpaUserExerciseMapper();
	}

	@Override
	public UserExercise save(UserExercise entity) {
		return
	}

	@Override
	public Optional<UserExercise> findById(int userId, int exerciseId) {
		return jpaUserExerciseDao.findById(new PublicUserExerciseId(userId, exerciseId))
			.map(jpaUserExerciseMapper::toDomain);
	}

	@Override
	public List<UserExercise> findAll() {
		return List.of();
	}

	@Override
	public Optional<UserExercise> findNextExerciseByLessonId(int publicUserId, int lessonId) {
		return Optional.empty();
	}

	@Override
	public Optional<UserExercise> findNextExerciseByUserId(int userId) {
		return Optional.empty();
	}

	@Override
	public Optional<Double> findAverageRateByLessonId(int lessonId) {
		return Optional.empty();
	}

	public class JpaUserExerciseMapper implements EntityMapper<UserExercise, PublicUserExerciseEntity> {
		@Override
		public UserExercise toDomain(PublicUserExerciseEntity entity) {
			return new UserExercise(
				entity.getId().getIdPublicUser(),
				entity.getId().getIdExercise(),
				entity.getRate(),
				entity.getNextReview()
			);
		}

		@Override
		public PublicUserExerciseEntity toEntity(UserExercise domain) {
			return new PublicUserExerciseEntity(
				new PublicUserExerciseId(domain.userId(), domain.exerciseId()),
				domain.rate(),
				domain.nextReview(),

			);
		}
	}
}
