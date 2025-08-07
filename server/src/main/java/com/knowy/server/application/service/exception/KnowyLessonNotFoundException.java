package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyLessonNotFoundException extends KnowyException {
	public KnowyLessonNotFoundException(String message) {
		super(message);
	}
}
