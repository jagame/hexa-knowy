package com.knowy.server.application.exception.data;

import com.knowy.server.application.exception.KnowyException;

public class KnowyDataAccessException extends KnowyException {
	public KnowyDataAccessException(String message) {
		super(message);
	}

	public KnowyDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
