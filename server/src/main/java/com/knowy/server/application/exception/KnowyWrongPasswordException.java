package com.knowy.server.application.exception;

public class KnowyWrongPasswordException extends KnowyException {
	public KnowyWrongPasswordException(String message) {
		super(message);
	}

	public KnowyWrongPasswordException(String message, Throwable cause) {
		super(message, cause);
	}
}
