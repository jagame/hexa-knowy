package com.knowy.server.controller.dto;

import com.knowy.server.entity.OptionEntity;

public record OptionsDto(
	String text,
	boolean isCorrect
) {

	public static OptionsDto fromEntity(OptionEntity option) {
		return new OptionsDto(option.getOptionText(), option.isCorrect());
	}
}
