package com.knowy.server.application.exception.validation.user;

import com.knowy.server.application.exception.validation.KnowyValidationException;

public class KnowyInvalidUserException extends KnowyValidationException {

	public KnowyInvalidUserException(String message) {
		super(message);
	}
}
