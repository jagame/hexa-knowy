package com.knowy.server.application.domain;

import com.knowy.server.infrastructure.adapters.repository.entity.LessonEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public record Course(
	int id,
	String title,
	String description,
	String image,
	String author,
	LocalDateTime creationDate,
	Set<Category> languages,
	List<LessonEntity> lessons
) {
	public Course(
		int id,
		String title,
		String description,
		String image,
		String author,
		LocalDateTime creationDate
	) {
		this(id, title, description, image, author, creationDate, new HashSet<>(), new ArrayList<>());
	}
}
