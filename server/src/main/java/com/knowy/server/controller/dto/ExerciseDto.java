package com.knowy.server.controller.dto;

import com.knowy.server.entity.ExerciseEntity;
import com.knowy.server.entity.PublicUserExerciseEntity;

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

	public static ExerciseDto fromPublicUserExerciseEntity(PublicUserExerciseEntity publicUserExercise) {
		ExerciseEntity exerciseEntity = publicUserExercise.getExerciseEntity();

		List<ExerciseOptionDto> options = exerciseEntity.getOptions()
			.stream()
			.map(ExerciseOptionDto::fromEntity)
			.toList();

		return new ExerciseDto(
			exerciseEntity.getLesson().getId(),
			exerciseEntity.getId(),
			exerciseEntity.getLesson().getCourse().getTitle(),
			publicUserExercise.getRate(),
			publicUserExercise.getExerciseEntity().getQuestion(),
			"TODO",
			options
		);
	}
}
