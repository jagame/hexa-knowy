package com.knowy.server.application.port.security;

import com.knowy.server.application.domain.error.KnowyException;

public class KnowySecurityException extends KnowyException {
	public KnowySecurityException(String message) {
		super(message);
	}

	public KnowySecurityException(Throwable cause) {
		super(cause);
	}

	public KnowySecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}
