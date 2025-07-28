package com.knowy.server.application.service.exception;

public class UnchangedNicknameException extends Exception {
	public UnchangedNicknameException(String message) {
		super(message);
	}

	public UnchangedNicknameException(String message, Throwable cause) {
		super(message, cause);
	}
}
