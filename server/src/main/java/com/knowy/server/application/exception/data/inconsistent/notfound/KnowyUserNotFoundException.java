package com.knowy.server.application.exception.data.inconsistent.notfound;

import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

public class KnowyUserNotFoundException extends KnowyInconsistentDataException {
	public KnowyUserNotFoundException(String message) {
		super(message);
	}
}
