package com.knowy.server.infrastructure.adapters.repository.exception;

import com.knowy.server.application.exception.KnowyInconsistentDataException;

public class JpaLessonNotFoundException extends KnowyInconsistentDataException {
	public JpaLessonNotFoundException(String message) {
		super(message);
	}
}
