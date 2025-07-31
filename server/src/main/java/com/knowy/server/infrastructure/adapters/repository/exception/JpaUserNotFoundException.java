package com.knowy.server.infrastructure.adapters.repository.exception;

import com.knowy.server.application.exception.KnowyInconsistentDataException;

public class JpaUserNotFoundException extends KnowyInconsistentDataException {
	public JpaUserNotFoundException(String message) {
		super(message);
	}
}
