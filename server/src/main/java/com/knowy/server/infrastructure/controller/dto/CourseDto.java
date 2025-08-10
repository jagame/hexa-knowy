package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.domain.Category;
import com.knowy.server.domain.Course;

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

	public static CourseDto fromDomain(Course course, List<LessonDto> lessons) {
		long completedLesson = lessons.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.COMPLETE)
			.count();

		int courseProgress = lessons.isEmpty()
			? 0
			: (int) ((completedLesson * 100.0) / lessons.size());

		return new CourseDto(
			course.id(),
			course.title(),
			courseProgress,
			lessons,
			course.description(),
			course.image(),
			course.categories().stream()
				.map(Category::name)
				.toList()
		);
	}
}
