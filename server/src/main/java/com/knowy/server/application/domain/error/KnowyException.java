package com.knowy.server.application.domain.error;

import java.util.UUID;

public class KnowyException extends Exception {

	private final UUID errorUUID;

	public KnowyException(String message) {
		this(message, null);
	}

	public KnowyException(Throwable cause) {
		this(null, cause);
	}

	public KnowyException(String message, Throwable cause) {
		super(message, cause);
		this.errorUUID = UUID.randomUUID();
	}

	@Override
	public String getMessage() {
		return "Knowy error " + errorUUID + " - " + super.getMessage();
	}

	public UUID errorUUID() {
		return errorUUID;
	}
}
