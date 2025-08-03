package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyUnchangedEmailException extends KnowyException {
	public KnowyUnchangedEmailException(String message) {
		super(message);
	}
}
