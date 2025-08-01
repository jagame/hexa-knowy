package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.application.domain.Category;
import com.knowy.server.application.domain.Course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public record CourseCardDTO(
	Integer id,
	String name,
	String author,
	int progress,
	ActionType action,
	List<String> categories,
	String image,
	LocalDateTime creationDate
) {

	public static CourseCardDTO fromDomain(Course course, int progress, ActionType actionType) {
		Objects.requireNonNull(actionType, "ActionType must not be null");

		return new CourseCardDTO(
			course.id(),
			course.title(),
			course.author(),
			progress,
			actionType,
			course.categories()
				.stream()
				.map(Category::name)
				.toList(),
			course.image(),
			course.creationDate()
		);
	}

	public enum ActionType {
		START,
		ACQUIRE
	}
}
