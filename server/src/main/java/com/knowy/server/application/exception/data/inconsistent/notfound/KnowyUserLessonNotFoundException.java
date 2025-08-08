package com.knowy.server.application.exception.data.inconsistent.notfound;

import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

public class KnowyUserLessonNotFoundException extends KnowyInconsistentDataException {
	public KnowyUserLessonNotFoundException(String message) {
		super(message);
	}
}
