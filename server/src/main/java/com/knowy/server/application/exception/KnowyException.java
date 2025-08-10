package com.knowy.server.application.exception;

import java.util.UUID;

public class KnowyException extends Exception {

	private final UUID exceptionUUID;

	public KnowyException(String message) {
		this(message, null);
	}

	public KnowyException(Throwable cause) {
		this(null, cause);
	}

	public KnowyException(String message, Throwable cause) {
		this(UUID.randomUUID(), message, cause);
	}

	private KnowyException(UUID exceptionUUID, String message, Throwable cause) {
		super(
			"Identified exception with uuid %s - %s".formatted(exceptionUUID.toString(), message),
			cause
		);
		this.exceptionUUID = exceptionUUID;
	}

	public UUID getExceptionUUID() {
		return exceptionUUID;
	}

}
