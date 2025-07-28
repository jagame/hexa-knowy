package com.knowy.server.application.service.exception;

public class InvalidUserPasswordFormatException extends InvalidUserException {
	public InvalidUserPasswordFormatException(String message) {
		super(message);
	}
}
