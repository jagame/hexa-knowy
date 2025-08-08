package com.knowy.server.application.exception.validation.user;

import com.knowy.server.application.exception.validation.KnowyValidationException;

public class KnowyUnchangedImageException extends KnowyValidationException {
	public KnowyUnchangedImageException(String message) {
		super(message);
	}
}
