package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.UserLesson;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.exception.KnowyUserNotFoundException;
import com.knowy.server.application.ports.UserLessonRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserLessonDao;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonIdEntity;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaUserLessonMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserLessonRepository implements UserLessonRepository {

	private final JpaUserLessonDao jpaUserLessonDao;
	private final JpaUserLessonMapper jpaUserLessonMapper;

	public JpaUserLessonRepository(JpaUserLessonDao jpaUserLessonDao, JpaUserLessonMapper jpaUserLessonMapper) {
		this.jpaUserLessonDao = jpaUserLessonDao;
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
	public UserLesson save(UserLesson userLesson) throws KnowyInconsistentDataException, KnowyUserNotFoundException {
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
}
