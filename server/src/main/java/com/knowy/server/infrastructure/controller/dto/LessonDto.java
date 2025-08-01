package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.application.domain.UserLesson;

import java.util.List;

public record LessonDto(
	int id,
	String title,
	String image,
	String duration,
	LessonStatus status
) {

	public static List<LessonDto> fromDomains(List<UserLesson> userLessons) {
		return userLessons.stream()
			.map(LessonDto::fromDomain)
			.toList();
	}

	public static LessonDto fromDomain(UserLesson userLesson) {
		return new LessonDto(
			userLesson.lesson().id(),
			userLesson.lesson().title(),
			null,
			null,
			LessonStatus.fromString(userLesson.status())
		);
	}

	public enum LessonStatus {
		COMPLETE,
		NEXT_LESSON,
		BLOCKED;

		public static LessonStatus fromString(UserLesson.ProgressStatus status) {
			return switch (status) {
				case UserLesson.ProgressStatus.COMPLETED -> LessonDto.LessonStatus.COMPLETE;
				case UserLesson.ProgressStatus.IN_PROGRESS -> LessonDto.LessonStatus.NEXT_LESSON;
				default -> LessonDto.LessonStatus.BLOCKED;
			};
		}
	}
}


