package com.knowy.server.application.exception.data.inconsistent.notfound;

import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

public class KnowyLessonNotFoundException extends KnowyInconsistentDataException {
	public KnowyLessonNotFoundException(String message) {
		super(message);
	}
}
