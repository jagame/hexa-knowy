package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyInvalidUserException extends KnowyException {

	public KnowyInvalidUserException(String message) {
		super(message);
	}

	public KnowyInvalidUserException(String message, Throwable cause) {
		super(message, cause);
	}

}
