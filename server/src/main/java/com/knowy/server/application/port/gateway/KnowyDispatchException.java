package com.knowy.server.application.port.gateway;

import com.knowy.server.application.domain.error.KnowyException;

public class KnowyDispatchException extends KnowyException {

	public KnowyDispatchException(String message) {
		super(message);
	}

	public KnowyDispatchException(String message, Throwable cause) {
		super(message, cause);
	}

	public KnowyDispatchException(Throwable cause) {
		super(cause);
	}

}
