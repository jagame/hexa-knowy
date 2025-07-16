package com.knowy.server.controller.dto;

import com.knowy.server.entity.OptionEntity;

public record ExerciseOptionDto(
	int id,
	String text,
	AnswerStatus status
) {

	public static ExerciseOptionDto fromEntity(OptionEntity option) {
		return new ExerciseOptionDto(option.getId(), option.getOptionText(), AnswerStatus.NO_RESPONSE);
	}

	public static ExerciseOptionDto fromEntity(OptionEntity option, int answerId) {
		return new ExerciseOptionDto(option.getId(), option.getOptionText(), AnswerStatus.from(option, answerId));
	}

	public enum AnswerStatus {
		NO_RESPONSE,
		RESPONSE_FAIL,
		RESPONSE_SUCCESS;

		public static AnswerStatus from(OptionEntity option, int answerId) {
			if (option.getId() != answerId) {
				return NO_RESPONSE;
			}
			return option.isCorrect() ? RESPONSE_SUCCESS : RESPONSE_FAIL;
		}
	}
}
