package com.knowy.server.application.domain.error;

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
