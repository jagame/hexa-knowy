package com.knowy.server.application.exception.validation.user;

public class KnowyWrongPasswordException extends KnowyInvalidUserException {
	public KnowyWrongPasswordException(String message) {
		super(message);
	}
}
