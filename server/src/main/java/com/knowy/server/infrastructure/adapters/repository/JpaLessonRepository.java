package com.knowy.server.infrastructure.adapters.repository;

import com.knowy.server.application.domain.Documentation;
import com.knowy.server.application.domain.Lesson;
import com.knowy.server.application.ports.LessonRepository;
import com.knowy.server.infrastructure.adapters.repository.dao.JpaLessonDao;
import com.knowy.server.infrastructure.adapters.repository.entity.DocumentationEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;
import com.knowy.server.infrastructure.adapters.repository.mapper.EntityMapper;
import com.knowy.server.infrastructure.adapters.repository.mapper.JpaExerciseMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JpaLessonRepository implements LessonRepository {

	private final JpaLessonDao jpaLessonDao;
	private final JpaExerciseMapper jpaExerciseMapper;
	private final JpaDocumentationMapper jpaDocumentationMapper;
	private final JpaLessonMapper jpaLessonMapper;

	public JpaLessonRepository(JpaLessonDao jpaLessonDao, JpaExerciseMapper jpaExerciseMapper) {
		this.jpaLessonDao = jpaLessonDao;
		this.jpaLessonMapper = new JpaLessonMapper();
		this.jpaExerciseMapper = jpaExerciseMapper;
		this.jpaDocumentationMapper = new JpaDocumentationMapper();
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

	public class JpaLessonMapper implements EntityMapper<Lesson, LessonEntity> {
		@Override
		public Lesson toDomain(LessonEntity entity) {
			return new Lesson(
				entity.getId(),
				entity.getCourse().getId(),
				entity.getNextLesson().getId(),
				entity.getTitle(),
				entity.getExplanation(),
				entity.getDocumentations().stream()
					.map(jpaDocumentationMapper::toDomain)
					.collect(Collectors.toSet()),
				entity.getExercises().stream()
					.map(jpaExerciseMapper::toDomain)
					.collect(Collectors.toSet())
			);
		}

		@Override
		public LessonEntity toEntity(Lesson domain) {
			return null;
		}
	}

	public class JpaDocumentationMapper implements EntityMapper<Documentation, DocumentationEntity> {
		@Override
		public Documentation toDomain(DocumentationEntity entity) {
			return new Documentation(entity.getId(), entity.getTitle(), entity.getLink());
		}

		@Override
		public DocumentationEntity toEntity(Documentation domain) {
			return new DocumentationEntity(
				domain.id(),
				domain.title(),
				domain.link(),
				jpaLessonDao.findByDocumentationId(domain.id())
			);
		}
	}
}
