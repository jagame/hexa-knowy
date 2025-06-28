package com.knowy.server.application.domain;

import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.InvalidKnowyEmailFormatException;

import java.util.Objects;

public record Email(String value) {

	public Email {
		Objects.requireNonNull(value, "An email value can't be null");
		if (!isValid(value)) {
			throw new InvalidKnowyEmailFormatException("An email must match the expression <user>@<domain>");
		}
	}

	public static void assertValid(String password) throws IllegalKnowyPasswordException {
		if (!isValid(password)) {
			throw new IllegalKnowyPasswordException("The minimum length of a password is 8 characters");
		}
	}

	public static boolean isValid(String emailValue) {
		return emailValue != null && emailValue.chars()
			.filter(ch -> ch == '@')
			.count() == 1;
	}

	public static void assertEquals(Email expected, Email candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked email doesn't match the value of the expected one"
			);
		}
	}

	public static boolean equals(Email expected, Email candidate) {
		Objects.requireNonNull(expected, "The expected email can't be null");
		return Objects.equals(expected, candidate);
	}

	public static void assertEquals(Email expected, String candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked email doesn't match the value of the expected one"
			);
		}
	}

	public static boolean equals(Email expected, String candidate) {
		Objects.requireNonNull(expected, "You need a non null email if you want to check another one");
		return equals(expected.value(), candidate);
	}

	public static void assertEquals(String expected, String candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked email doesn't match with the expected one"
			);
		}
	}

	public static boolean equals(String expected, String candidate) {
		Objects.requireNonNull(expected, "You need a non null email if you want to check another one");
		return Objects.equals(expected, candidate);
	}

	public void assertEquals(Email password) throws IllegalKnowyEmailException {
		assertEquals(password.value());
	}

	public void assertEquals(String candidate) throws IllegalKnowyEmailException {
		if (!hasValue(candidate)) {
			throw new IllegalKnowyEmailException("The checked password doesn't match with this one");
		}
	}

	public boolean hasValue(String testedValue) {
		return Objects.equals(this.value, testedValue);
	}
}
