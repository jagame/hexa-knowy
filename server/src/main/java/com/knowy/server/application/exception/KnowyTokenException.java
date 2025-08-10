package com.knowy.server.application.exception;

public class KnowyTokenException extends KnowyException {
	public KnowyTokenException(String message) {
		super(message);
	}

	public KnowyTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}
