package com.knowy.server.controller.dto;

import com.knowy.server.entity.OptionEntity;

public record ExerciseOptionDto(
	int id,
	String text,
	boolean isCorrect
) {

	public static ExerciseOptionDto fromEntity(OptionEntity option) {
		return new ExerciseOptionDto(option.getId(), option.getOptionText(), option.isCorrect());
	}
}
