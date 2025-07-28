package com.knowy.server.application.service.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
		super(message);
	}
}
