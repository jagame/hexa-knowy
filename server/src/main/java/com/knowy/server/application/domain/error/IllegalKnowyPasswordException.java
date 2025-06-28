package com.knowy.server.application.domain.error;

public class IllegalKnowyPasswordException extends IllegalKnowyDataException {
	public IllegalKnowyPasswordException(String message) {
		super(message);
	}
}
