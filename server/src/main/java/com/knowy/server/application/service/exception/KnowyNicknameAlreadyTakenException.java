package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyNicknameAlreadyTakenException extends KnowyException {
	public KnowyNicknameAlreadyTakenException(String message) {
		super(message);
	}

	public KnowyNicknameAlreadyTakenException(String message, Throwable cause) {
		super(message, cause);
	}
}
