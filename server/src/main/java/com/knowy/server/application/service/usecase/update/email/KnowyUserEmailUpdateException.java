package com.knowy.server.application.service.usecase.update.email;

import com.knowy.server.application.domain.error.KnowyException;

public class KnowyUserEmailUpdateException extends KnowyException {
	public KnowyUserEmailUpdateException(String message) {
		super(message);
	}

	KnowyUserEmailUpdateException(Throwable cause) {
		super(cause);
	}
}
