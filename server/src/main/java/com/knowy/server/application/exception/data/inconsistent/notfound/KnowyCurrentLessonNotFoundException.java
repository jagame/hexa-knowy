package com.knowy.server.application.exception.data.inconsistent.notfound;

import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

public class KnowyCurrentLessonNotFoundException extends KnowyInconsistentDataException {
	public KnowyCurrentLessonNotFoundException(String message) {
		super(message);
	}
}
