package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyUserNotFoundException extends KnowyException {
	public KnowyUserNotFoundException(String message) {
		super(message);
	}
}
