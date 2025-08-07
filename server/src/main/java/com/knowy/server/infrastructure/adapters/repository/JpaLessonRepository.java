package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.Lesson;
import com.knowy.server.application.ports.LessonRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaLessonDao;
import com.knowy.server.infrastructure.adapters.repository.mapper.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class JpaLessonRepository implements LessonRepository {

	private final JpaLessonDao jpaLessonDao;
	private final JpaLessonMapper jpaLessonMapper;

	public JpaLessonRepository(JpaLessonDao jpaLessonDao, JpaLessonMapper jpaLessonMapper) {
		this.jpaLessonDao = jpaLessonDao;
		this.jpaLessonMapper = jpaLessonMapper;
	}

	@Override
	public Optional<Lesson> findById(Integer id) {
		return jpaLessonDao.findById(id).map(jpaLessonMapper::toDomain);
	}

	@Override
	public List<Lesson> findByCourseId(Integer courseId) {
		return jpaLessonDao.findByCourseId(courseId).stream()
			.map(jpaLessonMapper::toDomain)
			.toList();
	}

	@Override
	public int countByCourseId(Integer courseId) {
		return jpaLessonDao.countByCourseId(courseId);
	}
}
