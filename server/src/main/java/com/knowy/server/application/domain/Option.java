package com.knowy.server.application.domain;

public record Option(
	int id,
	String optionText,
	boolean isCorrect
) {
}
