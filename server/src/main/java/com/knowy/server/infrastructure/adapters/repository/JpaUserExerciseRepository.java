package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.UserExercise;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.ports.UserExerciseRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaExerciseDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserExerciseDao;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserExerciseEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserExerciseId;
import com.knowy.server.infrastructure.adapters.repository.exception.JpaExerciseNotFoundException;
import com.knowy.server.infrastructure.adapters.repository.exception.JpaUserNotFoundException;
import com.knowy.server.infrastructure.adapters.repository.mapper.EntityMapper;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaExerciseMapper;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaUserMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserExerciseRepository implements UserExerciseRepository {

	private final JpaUserDao jpaUserDao;
	private final JpaExerciseDao jpaExerciseDao;
	private final JpaUserExerciseDao jpaUserExerciseDao;
	private final JpaUserExerciseMapper jpaUserExerciseMapper;
	private final JpaUserMapper jpaUserMapper;
	private final JpaExerciseMapper jpaExerciseMapper;

	public JpaUserExerciseRepository(JpaUserDao jpaUserDao, JpaExerciseDao jpaExerciseDao, JpaUserExerciseDao jpaUserExerciseDao, JpaUserMapper jpaUserMapper, JpaExerciseMapper jpaExerciseMapper) {
		this.jpaUserDao = jpaUserDao;
		this.jpaExerciseDao = jpaExerciseDao;
		this.jpaUserExerciseDao = jpaUserExerciseDao;
		this.jpaUserMapper = jpaUserMapper;
		this.jpaExerciseMapper = jpaExerciseMapper;
		this.jpaUserExerciseMapper = new JpaUserExerciseMapper();
	}

	@Override
	public UserExercise save(UserExercise userExercise) throws KnowyInconsistentDataException {
		PublicUserExerciseEntity userSaved = jpaUserExerciseDao.save(jpaUserExerciseMapper.toEntity(userExercise));
		return jpaUserExerciseMapper.toDomain(userSaved);
	}

	@Override
	public Optional<UserExercise> findById(int userId, int exerciseId) {
		return jpaUserExerciseDao.findById(new PublicUserExerciseId(userId, exerciseId))
			.map(jpaUserExerciseMapper::toDomain);
	}

	@Override
	public List<UserExercise> findAll() {
		return jpaUserExerciseDao.findAll()
			.stream()
			.map(jpaUserExerciseMapper::toDomain)
			.toList();
	}

	@Override
	public Optional<UserExercise> findNextExerciseByLessonId(int userId, int lessonId) {
		return jpaUserExerciseDao.findNextExerciseByLessonId(userId, lessonId)
			.map(jpaUserExerciseMapper::toDomain);
	}

	@Override
	public Optional<UserExercise> findNextExerciseByUserId(int userId) {
		return jpaUserExerciseDao.findNextExerciseByUserId(userId)
			.map(jpaUserExerciseMapper::toDomain);
	}

	@Override
	public Optional<Double> findAverageRateByLessonId(int lessonId) {
		return jpaUserExerciseDao.findAverageRateByLessonId(lessonId);
	}

	public class JpaUserExerciseMapper implements EntityMapper<UserExercise, PublicUserExerciseEntity> {
		@Override
		public UserExercise toDomain(PublicUserExerciseEntity entity) {
			return new UserExercise(
				jpaUserMapper.toDomain(entity.getPublicUserEntity()),
				jpaExerciseMapper.toDomain(entity.getExerciseEntity()),
				entity.getRate(),
				entity.getNextReview()
			);
		}

		@Override
		public PublicUserExerciseEntity toEntity(UserExercise domain) throws JpaUserNotFoundException, JpaExerciseNotFoundException {
			return new PublicUserExerciseEntity(
				new PublicUserExerciseId(domain.user().id(), domain.exercise().id()),
				domain.rate(),
				domain.nextReview(),
				jpaUserDao.findById(domain.user().id())
					.orElseThrow(() -> new JpaUserNotFoundException("User with ID " + domain.user().id() + " not found")),
				jpaExerciseDao.findById(domain.exercise().id())
					.orElseThrow(() -> new JpaExerciseNotFoundException("Exercise with ID: " + domain.exercise().id() +
						" not found"))
			);
		}
	}
}
