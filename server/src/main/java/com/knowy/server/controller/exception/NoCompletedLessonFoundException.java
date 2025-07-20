package com.knowy.server.controller.exception;

public class NoCompletedLessonFoundException extends Exception {
	public NoCompletedLessonFoundException(String message) {
		super(message);
	}
}
