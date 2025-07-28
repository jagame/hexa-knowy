package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.infrastructure.adapters.repository.entity.CourseEntity;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record NewsDto(
	String title,
	String date,
	String text
) {

	public static NewsDto fromEntity(CourseEntity course) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", Locale.forLanguageTag("es-ES"));
		String formattedDate = course.getCreationDate().format(formatter);

		return new NewsDto(
			course.getTitle(),
			formattedDate,
			course.getDescription()
		);
	}
}

