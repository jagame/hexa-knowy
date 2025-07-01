package com.knowy.server.util.exception;

public class JsonKnowyException extends Exception {
	public JsonKnowyException(String message) {
		super(message);
	}

	public JsonKnowyException(String message, Throwable cause) {
		super(message, cause);
	}
}
