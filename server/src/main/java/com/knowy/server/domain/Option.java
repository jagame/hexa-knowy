package com.knowy.server.domain;

public record Option(
	int id,
	String optionText,
	boolean isCorrect
) {
}
