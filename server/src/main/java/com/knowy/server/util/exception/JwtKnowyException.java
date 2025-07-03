package com.knowy.server.util.exception;

import com.knowy.server.application.domain.error.KnowySecurityException;

public class JwtKnowyException extends KnowySecurityException {
	public JwtKnowyException(String message) {
		super(message);
	}

	public JwtKnowyException(String message, Throwable cause) {
		super(message, cause);
	}
}
