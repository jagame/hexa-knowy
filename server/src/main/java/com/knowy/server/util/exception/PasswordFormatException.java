package com.knowy.server.util.exception;

public class PasswordFormatException extends Exception {
	public PasswordFormatException(String message) {
		super(message);
	}

	public PasswordFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
