package com.knowy.server.application.exception;

public class KnowyMailDispatchException extends KnowyException {
	public KnowyMailDispatchException(String message) {
		super(message);
	}

	public KnowyMailDispatchException(String message, Throwable cause) {
		super(message, cause);
	}
}
