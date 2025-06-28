package com.knowy.server.application.port.persistence;

public class KnowyUserNotFoundException extends KnowyPersistenceException {

	public KnowyUserNotFoundException(String message) {
		super(message);
	}
}
