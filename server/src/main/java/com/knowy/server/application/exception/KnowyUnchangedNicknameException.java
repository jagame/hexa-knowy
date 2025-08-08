package com.knowy.server.application.exception;

public class KnowyUnchangedNicknameException extends KnowyException {
	public KnowyUnchangedNicknameException(String message) {
		super(message);
	}

	public KnowyUnchangedNicknameException(String message, Throwable cause) {
		super(message, cause);
	}
}
