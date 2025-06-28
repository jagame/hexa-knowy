package com.knowy.server.application.domain.error;

public class InvalidKnowyDataFormatException extends KnowyRuntimeException {
	public InvalidKnowyDataFormatException(String message) {
		super(message);
	}
}
