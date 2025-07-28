package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.infrastructure.adapters.repository.entity.ExerciseEntity;
import com.knowy.server.infrastructure.adapters.repository.entity.OptionEntity;

import java.util.Collection;
import java.util.List;

public record SolutionDto(
	String cardTitle,
	String question,
	List<String> answer
) {

	public static List<SolutionDto> fromEntities(Collection<ExerciseEntity> exercises) {
		return exercises.stream()
			.map(SolutionDto::fromEntity)
			.toList();
	}

	public static SolutionDto fromEntity(ExerciseEntity exercise) {
		return new SolutionDto(
			"Ejercicio ",
			exercise.getQuestion(),
			exercise.getOptions().stream()
				.filter(OptionEntity::isCorrect)
				.map(OptionEntity::getOptionText)
				.toList()
		);
	}
}