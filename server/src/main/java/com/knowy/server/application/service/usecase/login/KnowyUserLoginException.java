package com.knowy.server.application.service.usecase.login;

import com.knowy.server.application.domain.error.KnowyException;

public class KnowyUserLoginException extends KnowyException {
	public KnowyUserLoginException(String message) {
		super(message);
	}

	KnowyUserLoginException(Throwable cause) {
		super(cause);
	}
}
