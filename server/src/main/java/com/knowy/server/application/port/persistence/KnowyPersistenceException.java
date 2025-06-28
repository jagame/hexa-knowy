package com.knowy.server.application.port.persistence;

import com.knowy.server.application.domain.error.KnowyException;

public class KnowyPersistenceException extends KnowyException {

	public KnowyPersistenceException(String message) {
		super(message);
	}

	public KnowyPersistenceException(String message, Throwable cause) {
		super(message, cause);
	}

	public KnowyPersistenceException(Throwable cause) {
		super(cause);
	}

}
