package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.domain.Course;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

public record NewsDto(
	String title,
	String date,
	String text
) {

	public static NewsDto fromDomain(Course course) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d 'de' MMMM, yyyy", Locale.forLanguageTag("es-ES"));
		String formattedDate = course.creationDate().format(formatter);

		return new NewsDto(
			course.title(),
			formattedDate,
			course.description()
		);
	}
}

