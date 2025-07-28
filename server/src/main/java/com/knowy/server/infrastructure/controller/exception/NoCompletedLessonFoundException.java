package com.knowy.server.infrastructure.controller.exception;

public class NoCompletedLessonFoundException extends Exception {
	public NoCompletedLessonFoundException(String message) {
		super(message);
	}
}
