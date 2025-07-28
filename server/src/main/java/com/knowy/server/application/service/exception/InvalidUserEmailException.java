package com.knowy.server.application.service.exception;

public class InvalidUserEmailException extends InvalidUserException {
	public InvalidUserEmailException(String message) {
		super(message);
	}
}
