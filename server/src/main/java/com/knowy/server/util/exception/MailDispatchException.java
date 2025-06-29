package com.knowy.server.util.exception;

public class MailDispatchException extends Exception {
	public MailDispatchException(String message) {
		super(message);
	}

	public MailDispatchException(String message, Throwable cause) {
		super(message, cause);
	}
}
