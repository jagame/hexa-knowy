package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyUnchangedNicknameException extends KnowyException {
	public KnowyUnchangedNicknameException(String message) {
		super(message);
	}

	public KnowyUnchangedNicknameException(String message, Throwable cause) {
		super(message, cause);
	}
}
