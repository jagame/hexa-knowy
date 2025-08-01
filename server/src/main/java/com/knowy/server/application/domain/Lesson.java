package com.knowy.server.application.domain;

import java.util.HashSet;
import java.util.Set;

public record Lesson(
	int id,
	Course course,
	Integer nextLessonId,
	String title,
	String explanation,
	Set<Documentation> documentations,
	Set<Exercise> exercises
) {
	public Lesson(
		int id,
		Course course,
		String title,
		String explanation
	) {
		this(id, course, null, title, explanation, new HashSet<>(), new HashSet<>());
	}
}