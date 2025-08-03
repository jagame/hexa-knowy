package com.knowy.server.application.service.exception;

import com.knowy.server.application.exception.KnowyException;

public class KnowyUnchangedImageException extends KnowyException {
	public KnowyUnchangedImageException(String message) {
		super(message);
	}
}
