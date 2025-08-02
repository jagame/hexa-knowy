package com.knowy.server.infrastructure.adapters.repository.mapper;

import com.knowy.server.application.domain.Course;
import com.knowy.server.infrastructure.adapters.repository.entity.CourseEntity;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class JpaCourseMapper implements EntityMapper<Course, CourseEntity> {

	private final JpaCategoryMapper jpaCategoryMapper;

	public JpaCourseMapper(JpaCategoryMapper jpaCategoryMapper) {
		this.jpaCategoryMapper = jpaCategoryMapper;
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
			entity.getLessons()
		);
	}

	@Override
	public CourseEntity toEntity(Course domain) {
		return null;
	}
}
