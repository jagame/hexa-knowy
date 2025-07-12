package com.knowy.server.service.exception;

public class InvalidUserPasswordFormatException extends InvalidUserException {
	public InvalidUserPasswordFormatException(String message) {
		super(message);
	}
}
