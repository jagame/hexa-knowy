package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.UserLesson;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaLessonDao;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaUserDao;
import com.knowy.server.infrastructure.adapters.repository.entity.PublicUserLessonEntity;
import com.knowy.server.infrastructure.adapters.repository.exception.JpaLessonNotFoundException;
import com.knowy.server.infrastructure.adapters.repository.exception.JpaUserNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JpaUserLessonMapper implements EntityMapper<UserLesson, PublicUserLessonEntity> {

	private final JpaUserMapper jpaUserMapper;
	private final JpaLessonMapper jpaLessonMapper;
	private final JpaUserDao jpaUserDao;
	private final JpaLessonDao jpaLessonDao;

	public JpaUserLessonMapper(JpaUserMapper jpaUserMapper, JpaLessonMapper jpaLessonMapper, JpaUserDao jpaUserDao, JpaLessonDao jpaLessonDao) {
		this.jpaUserMapper = jpaUserMapper;
		this.jpaLessonMapper = jpaLessonMapper;
		this.jpaUserDao = jpaUserDao;
		this.jpaLessonDao = jpaLessonDao;
	}

	@Override
	public UserLesson toDomain(PublicUserLessonEntity entity) {
		return new UserLesson(
			jpaUserMapper.toDomain(entity.getPublicUserEntity()),
			jpaLessonMapper.toDomain(entity.getLessonEntity()),
			entity.getStartDate(),
			UserLesson.ProgressStatus
				.valueOf(entity.getStatus().toUpperCase())
		);
	}

	@Override
	public PublicUserLessonEntity toEntity(UserLesson domain) throws JpaUserNotFoundException, JpaLessonNotFoundException {
		return new PublicUserLessonEntity(
			domain.user().id(),
			domain.lesson().id(),
			domain.startDate(),
			domain.status().name().toLowerCase(),
			jpaUserDao.findById(domain.user().id())
				.orElseThrow(() -> new JpaUserNotFoundException("User with ID: " + domain.user().id() +
					" not found")),
			jpaLessonDao.findById(domain.lesson().id())
				.orElseThrow(() -> new JpaLessonNotFoundException("Lesson with ID: " + domain.lesson().id() +
					" not found"))
		);
	}
}
