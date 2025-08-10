package com.knowy.server.application.exception.validation.user;

import com.knowy.server.application.exception.validation.KnowyValidationException;

public class KnowyUnchangedEmailException extends KnowyValidationException {
	public KnowyUnchangedEmailException(String message) {
		super(message);
	}
}
