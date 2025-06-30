package com.knowy.server.util.exception;

public class JwtKnowyException extends Exception {
	public JwtKnowyException(String message) {
		super(message);
	}

	public JwtKnowyException(String message, Throwable cause) {
		super(message, cause);
	}
}
