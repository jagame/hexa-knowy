package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.Lesson;
import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class JpaLessonMapper implements EntityMapper<Lesson, LessonEntity> {

	private final JpaCourseMapper jpaCourseMapper;
	private final JpaDocumentationMapper jpaDocumentationMapper;
	private final JpaExerciseMapper jpaExerciseMapper;

	public JpaLessonMapper(
		JpaCourseMapper jpaCourseMapper,
		JpaDocumentationMapper jpaDocumentationMapper,
		JpaExerciseMapper jpaExerciseMapper
	) {
		this.jpaCourseMapper = jpaCourseMapper;
		this.jpaDocumentationMapper = jpaDocumentationMapper;
		this.jpaExerciseMapper = jpaExerciseMapper;
	}

	@Override
	public Lesson toDomain(LessonEntity entity) {
		return new Lesson(
			entity.getId(),
			jpaCourseMapper.toDomain(entity.getCourse()),
			Optional.ofNullable(entity.getNextLesson())
				.map(LessonEntity::getId)
				.orElse(null),
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
