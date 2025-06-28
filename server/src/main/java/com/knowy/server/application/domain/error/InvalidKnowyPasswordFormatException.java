package com.knowy.server.application.domain.error;

public class InvalidKnowyPasswordFormatException extends InvalidKnowyDataFormatException {
	public InvalidKnowyPasswordFormatException(String message) {
		super(message);
	}
}
