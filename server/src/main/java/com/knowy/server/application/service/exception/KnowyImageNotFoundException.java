package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyImageNotFoundException extends KnowyException {
	public KnowyImageNotFoundException(String message) {
		super(message);
	}
}
