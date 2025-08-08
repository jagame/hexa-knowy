package com.knowy.server.application.exception.validation.user;

import com.knowy.server.application.exception.validation.KnowyValidationException;

public class KnowyPasswordFormatException extends KnowyValidationException {
	public KnowyPasswordFormatException(String message) {
		super(message);
	}
}
