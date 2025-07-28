package com.knowy.server.infrastructure.controller.exception;

public class CurrentLessonNotFoundException extends Exception {
	public CurrentLessonNotFoundException(String message) {
		super(message);
	}
}
