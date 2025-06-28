package com.knowy.server.application.service.usecase.update.email;

public class InvalidKnowyUserEmailException extends KnowyUserEmailUpdateException {
	public InvalidKnowyUserEmailException(String message) {
		super(message);
	}
}
