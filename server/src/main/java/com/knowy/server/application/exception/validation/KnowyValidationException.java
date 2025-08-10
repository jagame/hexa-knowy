package com.knowy.server.application.exception.validation;

import com.knowy.server.application.exception.KnowyException;

public class KnowyValidationException extends KnowyException {
	public KnowyValidationException(String message) {
		super(message);
	}
}
