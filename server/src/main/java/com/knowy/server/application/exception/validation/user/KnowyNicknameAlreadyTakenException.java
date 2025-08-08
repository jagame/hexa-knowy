package com.knowy.server.application.exception.validation.user;

import com.knowy.server.application.exception.validation.KnowyValidationException;

public class KnowyNicknameAlreadyTakenException extends KnowyValidationException {
	public KnowyNicknameAlreadyTakenException(String message) {
		super(message);
	}
}
