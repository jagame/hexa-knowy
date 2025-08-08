package com.knowy.server.application.exception;

public class KnowyInvalidUserException extends KnowyException {

	public KnowyInvalidUserException(String message) {
		super(message);
	}

	public KnowyInvalidUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
