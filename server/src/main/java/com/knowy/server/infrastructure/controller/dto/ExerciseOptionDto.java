package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.application.domain.Option;
import com.knowy.server.infrastructure.adapters.repository.entity.OptionEntity;

public record ExerciseOptionDto(
	int id,
	String text,
	AnswerStatus status
) {

	public static ExerciseOptionDto fromDomain(Option option) {
		return new ExerciseOptionDto(option.id(), option.optionText(), AnswerStatus.NO_RESPONSE);
	}

	public static ExerciseOptionDto fromDomain(Option option, int answerId) {
		return new ExerciseOptionDto(option.id(), option.optionText(), AnswerStatus.from(option, answerId));
	}

	public enum AnswerStatus {
		NO_RESPONSE,
		RESPONSE_FAIL,
		RESPONSE_SUCCESS;

		public static AnswerStatus from(Option option, int answerId) {
			if (option.id() == answerId && !option.isCorrect()) {
				return RESPONSE_FAIL;
			}
			if (option.isCorrect()) {
				return RESPONSE_SUCCESS;
			}
			return NO_RESPONSE;
		}
	}
}
