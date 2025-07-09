package com.knowy.server.service.exception;

public class NicknameAlreadyTakenException extends Exception {
	public NicknameAlreadyTakenException(String message) {
		super(message);
	}

	public NicknameAlreadyTakenException(String message, Throwable cause) {
		super(message, cause);
	}
}
