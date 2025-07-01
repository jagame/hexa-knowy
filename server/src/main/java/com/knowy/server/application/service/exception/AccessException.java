package com.knowy.server.application.service.exception;

public class AccessException extends Exception {
	public AccessException(String message) {
		super(message);
	}

	public AccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
