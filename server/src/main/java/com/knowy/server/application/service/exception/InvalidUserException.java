package com.knowy.server.application.service.exception;

import com.knowy.server.application.domain.error.KnowyException;

public class InvalidUserException extends KnowyException {

	public InvalidUserException(String message) {
		super(message);
	}

	public InvalidUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
