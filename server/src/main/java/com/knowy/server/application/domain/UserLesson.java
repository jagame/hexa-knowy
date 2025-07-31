package com.knowy.server.application.domain;

import java.time.LocalDate;

public record UserLesson(
	Integer userId,
	Integer lessonId,
	LocalDate startDate,
	ProgressStatus status
) {

	public enum ProgressStatus {
		PENDING,
		IN_PROGRESS,
		COMPLETED
	}
}
