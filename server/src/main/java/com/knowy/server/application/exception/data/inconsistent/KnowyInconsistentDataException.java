package com.knowy.server.application.exception.data.inconsistent;

import com.knowy.server.application.exception.data.KnowyDataAccessException;

public class KnowyInconsistentDataException extends KnowyDataAccessException {
	public KnowyInconsistentDataException(String message) {
		super(message);
	}
}
