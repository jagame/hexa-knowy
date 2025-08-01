package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.application.domain.Lesson;
import com.knowy.server.application.domain.UserExercise;

import java.util.List;

public record ExerciseDto(
	int lessonId,
	int exerciseId,
	String courseName,
	int correctPercentage,
	String questionText,
	String imgPath,
	List<ExerciseOptionDto> options
) {

	public static ExerciseDto fromDomain(UserExercise userExercise, Lesson lesson) {
		return new ExerciseDto(
			userExercise.exercise().lessonId(),
			userExercise.exercise().id(),
			lesson.course().title(),
			userExercise.rate(),
			userExercise.exercise().question(),
			"TODO",
			userExercise.exercise().options()
				.stream()
				.map(ExerciseOptionDto::fromDomain)
				.toList()
		);
	}

	public static ExerciseDto fromDomain(UserExercise userExercise, Lesson lesson, int answerId) {
		List<ExerciseOptionDto> options = userExercise.exercise().options()
			.stream()
			.map(option -> ExerciseOptionDto.fromDomain(option, answerId))
			.toList();

		return new ExerciseDto(
			userExercise.exercise().lessonId(),
			userExercise.exercise().id(),
			lesson.course().title(),
			userExercise.rate(),
			userExercise.exercise().question(),
			"TODO",
			options
		);
	}
}
