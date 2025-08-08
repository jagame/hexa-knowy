package com.knowy.server.application.domain;

import java.util.HashSet;
import java.util.Set;

public record Lesson(
	int id,
	int courseId,
	Integer nextLessonId,
	String title,
	String explanation,
	Set<Documentation> documentations,
	Set<Exercise> exercises
) {
	public Lesson(
		int id,
		int courseId,
		String title,
		String explanation
	) {
		this(id, courseId, null, title, explanation, new HashSet<>(), new HashSet<>());
	}
}