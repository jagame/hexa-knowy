package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.domain.Exercise;
import com.knowy.server.domain.Option;

import java.util.Collection;
import java.util.List;

public record SolutionDto(
	String cardTitle,
	String question,
	List<String> answer
) {

	public static List<SolutionDto> fromDomains(Collection<Exercise> exercises) {
		return exercises.stream()
			.map(SolutionDto::fromDomain)
			.toList();
	}

	public static SolutionDto fromDomain(Exercise exercise) {
		return new SolutionDto(
			"Ejercicio ",
			exercise.question(),
			exercise.options().stream()
				.filter(Option::isCorrect)
				.map(Option::optionText)
				.toList()
		);
	}
}