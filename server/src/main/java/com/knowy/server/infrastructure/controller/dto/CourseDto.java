package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.infrastructure.adapters.repository.entity.CourseEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.LanguageEntity;

import java.util.List;

public record CourseDto(
	int id,
	String name,
	int percentageCompleted,
	List<LessonDto> lessons,
	String description,
	String image,
	List<String> languages
) {

	public static CourseDto fromEntity(CourseEntity course, List<LessonDto> lessons) {
		long completedLesson = lessons.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.COMPLETE)
			.count();

		int courseProgress = lessons.isEmpty()
			? 0
			: (int) ((completedLesson * 100.0) / lessons.size());

		return new CourseDto(
			course.getId(),
			course.getTitle(),
			courseProgress,
			lessons,
			course.getDescription(),
			course.getImage(),
			course.getLanguages().stream()
				.map(LanguageEntity::getName)
				.toList()
		);
	}
}
