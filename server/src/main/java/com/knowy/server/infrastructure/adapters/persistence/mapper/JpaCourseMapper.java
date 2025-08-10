package com.knowy.server.infrastructure.adapters.persistence.mapper;

import com.knowy.server.domain.Course;
import com.knowy.server.infrastructure.adapters.persistence.entity.CourseEntity;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JpaCourseMapper implements EntityMapper<Course, CourseEntity> {

	private final JpaCategoryMapper jpaCategoryMapper;
	private final JpaLessonMapper jpaLessonMapper;

	public JpaCourseMapper(JpaCategoryMapper jpaCategoryMapper,@Lazy JpaLessonMapper jpaLessonMapper) {
		this.jpaCategoryMapper = jpaCategoryMapper;
		this.jpaLessonMapper = jpaLessonMapper;
	}

	@Override
	public Course toDomain(CourseEntity entity) {
		return new Course(
			entity.getId(),
			entity.getTitle(),
			entity.getDescription(),
			entity.getImage(),
			entity.getAuthor(),
			entity.getCreationDate(),
			entity.getLanguages().stream().map(jpaCategoryMapper::toDomain).collect(Collectors.toSet()),
			entity.getLessons().stream().map(jpaLessonMapper::toDomain).collect(Collectors.toSet())
		);
	}

	@Override
	public CourseEntity toEntity(Course domain) {
		return null;
	}
}
