package com.knowy.server.application.exception;

public class KnowyPasswordFormatException extends KnowyException {
	public KnowyPasswordFormatException(String message) {
		super(message);
	}

	public KnowyPasswordFormatException(String message, Throwable cause) {
		super(message, cause);
	}
}
