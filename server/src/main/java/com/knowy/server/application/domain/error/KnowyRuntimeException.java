package com.knowy.server.application.domain.error;

import java.util.UUID;

public class KnowyRuntimeException extends RuntimeException {

	private final UUID errorUUID;

	public KnowyRuntimeException(String message) {
		this(message, null);
	}

	public KnowyRuntimeException(Throwable cause) {
		this(null, cause);
	}

	public KnowyRuntimeException(String message, Throwable cause) {
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
