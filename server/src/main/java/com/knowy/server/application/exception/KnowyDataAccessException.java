package com.knowy.server.application.exception;

public class KnowyDataAccessException extends Exception {
	public KnowyDataAccessException(String message) {
		super(message);
	}

	public KnowyDataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
