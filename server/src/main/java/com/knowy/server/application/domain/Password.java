package com.knowy.server.application.domain;

import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.InvalidKnowyPasswordFormatException;

import java.util.Objects;

public record Password(String value) {

	public Password {
		Objects.requireNonNull(value, "A password value can't be null");
		if (!isValid(value)) {
			throw new InvalidKnowyPasswordFormatException("The minimum length of a password is 8 characters");
		}
	}

	public static void assertValid(String password) throws IllegalKnowyPasswordException {
		if (!isValid(password)) {
			throw new IllegalKnowyPasswordException("The minimum length of a password is 8 characters");
		}
	}

	public static boolean isValid(String passwordValue) {
		return passwordValue != null && passwordValue.length() >= 8;
	}

	public static void assertEquals(Password expected, Password candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked password doesn't match the value of the expected one"
			);
		}
	}

	public static boolean equals(Password expected, Password candidate) {
		Objects.requireNonNull(expected, "The expected password can't be null");
		return Objects.equals(expected, candidate);
	}

	public static void assertEquals(Password expected, String candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked password doesn't match the value of the expected one"
			);
		}
	}

	public static boolean equals(Password expected, String candidate) {
		Objects.requireNonNull(expected, "You need a non null password if you want to check another one");
		return equals(expected.value(), candidate);
	}

	public static void assertEquals(String expected, String candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked password doesn't match with the expected one"
			);
		}
	}

	public static boolean equals(String expected, String candidate) {
		Objects.requireNonNull(expected, "You need a non null password if you want to check another one");
		return Objects.equals(expected, candidate);
	}

	public void assertEquals(Password password) throws IllegalKnowyPasswordException {
		assertEquals(password.value());
	}

	public void assertEquals(String candidate) throws IllegalKnowyPasswordException {
		if (!hasValue(candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked password doesn't match with this one"
			);
		}
	}

	public boolean hasValue(String testedValue) {
		return Objects.equals(this.value, testedValue);
	}

}
