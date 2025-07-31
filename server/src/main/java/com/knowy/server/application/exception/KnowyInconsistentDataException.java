package com.knowy.server.application.exception;

public class KnowyInconsistentDataException extends KnowyDataAccessException {
	public KnowyInconsistentDataException(String message) {
		super(message);
	}
}
