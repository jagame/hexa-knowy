package com.knowy.server.controller.exception;

public class KnowyValidationException extends RuntimeException {
	public KnowyValidationException(String message) {
		super(message);
	}
}
