package com.knowy.server.infrastructure.adapters.repository.exception;

import com.knowy.server.application.exception.KnowyInconsistentDataException;

public class JpaExerciseNotFoundException extends KnowyInconsistentDataException {
	public JpaExerciseNotFoundException(String message) {
		super(message);
	}
}
