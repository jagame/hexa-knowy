package com.knowy.server.application.exception;

public class KnowyNicknameAlreadyTakenException extends KnowyException {
	public KnowyNicknameAlreadyTakenException(String message) {
		super(message);
	}

	public KnowyNicknameAlreadyTakenException(String message, Throwable cause) {
		super(message, cause);
	}
}
