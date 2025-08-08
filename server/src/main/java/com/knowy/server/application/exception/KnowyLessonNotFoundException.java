package com.knowy.server.application.exception;

public class KnowyLessonNotFoundException extends KnowyInconsistentDataException {
	public KnowyLessonNotFoundException(String message) {
		super(message);
	}
}
