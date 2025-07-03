package com.knowy.server.application.service.usecase.update.password;

import com.knowy.server.application.domain.error.KnowySecurityException;

public class KnowyUserPasswordUpdateException extends KnowySecurityException {
	public KnowyUserPasswordUpdateException(String message) {
		super(message);
	}

	public KnowyUserPasswordUpdateException(Throwable cause) {
		super(cause);
	}

	public KnowyUserPasswordUpdateException(String message, Throwable cause) {
		super(message, cause);
	}
}
