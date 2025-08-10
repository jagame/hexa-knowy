package com.knowy.server.application.exception.data.inconsistent.notfound;

import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

public class KnowyExerciseNotFoundException extends KnowyInconsistentDataException {
	public KnowyExerciseNotFoundException(String message) {
		super(message);
	}
}
