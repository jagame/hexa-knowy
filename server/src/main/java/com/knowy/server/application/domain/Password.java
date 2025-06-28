package com.knowy.server.application.domain;

import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.InvalidKnowyPasswordFormatException;
import com.knowy.server.application.domain.validation.GenericValidator;

import java.util.Objects;

public record Password(String value) implements ValueObject<String> {

	private static final GenericValidator<String, Password, IllegalKnowyPasswordException> PASSWORD_VALIDATOR =
		new GenericValidator<>(
			"password",
			"The minimum length of a password is 8 characters",
			passwordValue -> passwordValue != null && passwordValue.length() >= 8,
			IllegalKnowyPasswordException::new
		);

	public Password {
		Objects.requireNonNull(value, "A password value can't be null");
		if (!isValid(value)) {
			throw new InvalidKnowyPasswordFormatException("The minimum length of a password is 8 characters");
		}
	}

	public static void assertValid(String password) throws IllegalKnowyPasswordException {
		PASSWORD_VALIDATOR.assertValid(password);
	}

	public static boolean isValid(String passwordValue) {
		return PASSWORD_VALIDATOR.isValid(passwordValue);
	}

	public static void assertEquals(Password expected, Password candidate) throws IllegalKnowyPasswordException {
		PASSWORD_VALIDATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Password expected, Password candidate) {
		return PASSWORD_VALIDATOR.equals(expected, candidate);
	}

	public static void assertEquals(Password expected, String candidate) throws IllegalKnowyPasswordException {
		PASSWORD_VALIDATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Password expected, String candidate) {
		return PASSWORD_VALIDATOR.equals(expected, candidate);
	}

	public static void assertEquals(String expected, String candidate) throws IllegalKnowyPasswordException {
		PASSWORD_VALIDATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(String expected, String candidate) {
		return PASSWORD_VALIDATOR.equals(expected, candidate);
	}

	public void assertEquals(Password other) throws IllegalKnowyPasswordException {
		assertEquals(this, other);
	}

	public void assertEquals(String other) throws IllegalKnowyPasswordException {
		assertEquals(this, other);
	}

	@Override
	public boolean hasValue(String testedValue) {
		return Objects.equals(this.value, testedValue);
	}

}
