package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.UserLesson;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.ports.UserLessonRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaLessonDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserLessonDao;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonIdEntity;
import com.knowy.server.infrastructure.adapters.repository.exception.JpaLessonNotFoundException;
import com.knowy.server.infrastructure.adapters.repository.exception.JpaUserNotFoundException;
import com.knowy.server.infrastructure.adapters.repository.mapper.EntityMapper;

import java.util.List;
import java.util.Optional;

public class JpaUserLessonRepository implements UserLessonRepository {

	private final JpaUserLessonDao jpaUserLessonDao;
	private final JpaUserDao jpaUserDao;
	private final JpaLessonDao jpaLessonDao;
	private final JpaUserLessonMapper jpaUserLessonMapper;

	public JpaUserLessonRepository(JpaUserLessonDao jpaUserLessonDao, JpaUserDao jpaUserDao, JpaLessonDao jpaLessonDao, JpaUserLessonMapper jpaUserLessonMapper) {
		this.jpaUserLessonDao = jpaUserLessonDao;
		this.jpaUserDao = jpaUserDao;
		this.jpaLessonDao = jpaLessonDao;
		this.jpaUserLessonMapper = jpaUserLessonMapper;
	}

	@Override
	public boolean existsByUserIdAndLessonId(Integer userId, Integer lessonId) {
		return jpaUserLessonDao.existsByUserIdAndLessonId(userId, lessonId);
	}

	@Override
	public List<Integer> findCourseIdsByUserId(Integer userId) {
		return jpaUserLessonDao.findCourseIdsByUserId(userId);
	}

	@Override
	public boolean existsById(int userId, int lessonId) {
		return jpaUserLessonDao.existsById(new PublicUserLessonIdEntity(userId, lessonId));
	}

	@Override
	public UserLesson save(UserLesson userLesson) throws KnowyInconsistentDataException {
		PublicUserLessonEntity publicUserLessonEntity = jpaUserLessonDao.save(jpaUserLessonMapper.toEntity(userLesson));
		return jpaUserLessonMapper.toDomain(publicUserLessonEntity);
	}

	@Override
	public int countByUserIdAndCourseIdAndStatus(Integer userId, Integer courseId, UserLesson.ProgressStatus status) {
		return jpaUserLessonDao.countByUserIdAndCourseIdAndStatus(userId, courseId, status.name().toLowerCase());
	}

	@Override
	public Optional<UserLesson> findById(int userId, int lessonId) {
		return jpaUserLessonDao.findById(new PublicUserLessonIdEntity(userId, lessonId))
			.map(jpaUserLessonMapper::toDomain);
	}

	@Override
	public List<UserLesson> findAllByCourseId(int userId, int courseId) {
		return jpaUserLessonDao.findAllByCourseId(userId, courseId)
			.stream()
			.map(jpaUserLessonMapper::toDomain)
			.toList();
	}

	public class JpaUserLessonMapper implements EntityMapper<UserLesson, PublicUserLessonEntity> {

		@Override
		public UserLesson toDomain(PublicUserLessonEntity entity) {
			return new UserLesson(
				entity.getUserId(),
				entity.getLessonId(),
				entity.getStartDate(),
				UserLesson.ProgressStatus
					.valueOf(entity.getStatus().toUpperCase())
			);
		}

		@Override
		public PublicUserLessonEntity toEntity(UserLesson domain) throws JpaUserNotFoundException, JpaLessonNotFoundException {
			return new PublicUserLessonEntity(
				domain.userId(),
				domain.lessonId(),
				domain.startDate(),
				domain.status().name().toLowerCase(),
				jpaUserDao.findById(domain.userId())
					.orElseThrow(() -> new JpaUserNotFoundException("User with ID: " + domain.userId() + " not found")),
				jpaLessonDao.findById(domain.lessonId())
					.orElseThrow(() -> new JpaLessonNotFoundException("Lesson with ID: " + domain.lessonId() +
						" not found"))
			);
		}
	}
}
