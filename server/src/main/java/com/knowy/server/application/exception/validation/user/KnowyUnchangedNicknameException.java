package com.knowy.server.application.exception.validation.user;

import com.knowy.server.application.exception.validation.KnowyValidationException;

public class KnowyUnchangedNicknameException extends KnowyValidationException {
	public KnowyUnchangedNicknameException(String message) {
		super(message);
	}
}
