package com.knowy.server.application.domain;

import java.time.LocalDate;

public record UserLesson(
	User user,
	Lesson lesson,
	LocalDate startDate,
	ProgressStatus status
) {

	public enum ProgressStatus {
		PENDING,
		IN_PROGRESS,
		COMPLETED
	}
}
