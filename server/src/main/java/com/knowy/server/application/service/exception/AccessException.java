package com.knowy.server.application.service.exception;

import com.knowy.server.application.domain.error.KnowySecurityException;

public class AccessException extends KnowySecurityException {
	public AccessException(String message) {
		super(message);
	}

	public AccessException(String message, Throwable cause) {
		super(message, cause);
	}
}
