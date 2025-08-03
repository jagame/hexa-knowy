package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyUserLessonNotFoundException extends KnowyException {
	public KnowyUserLessonNotFoundException(String message) {
		super(message);
	}
}
