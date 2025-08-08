package com.knowy.server.application.exception.data.inconsistent.notfound;

import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

public class KnowyImageNotFoundException extends KnowyInconsistentDataException {
	public KnowyImageNotFoundException(String message) {
		super(message);
	}
}
