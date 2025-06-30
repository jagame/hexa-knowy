package com.knowy.server.util.exception;

public class WrongPasswordException extends Exception {
	public WrongPasswordException(String message) {
		super(message);
	}

	public WrongPasswordException(String message, Throwable cause) {
		super(message, cause);
	}
}
