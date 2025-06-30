package com.knowy.server.application.domain;

import com.knowy.server.application.domain.assertion.Assert;
import com.knowy.server.application.domain.assertion.ValueObjectComparator;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.InvalidKnowyPasswordFormatException;

import java.util.Objects;

public record Password(String value) implements ValueObject<String> {

	private static final String VALIDATION_ERROR_MESSAGE = "The minimum length of a password is 8 characters";
	private static final Assert.AssertionBuilder<String> ASSERT_IS_VALID_PASSWORD = Assert.that(Password::isValid);
	private static final ValueObjectComparator<String, Password, IllegalKnowyPasswordException> PASSWORD_COMPARATOR =
		new ValueObjectComparator<>(
			"password",
			IllegalKnowyPasswordException::new
		);

	public Password {
		Objects.requireNonNull(value, "A password value can't be null");
		ASSERT_IS_VALID_PASSWORD
			.orElseThrow(() -> new InvalidKnowyPasswordFormatException(VALIDATION_ERROR_MESSAGE))
			.value(value);
	}

	public static boolean isValid(String passwordValue) {
		return passwordValue != null && passwordValue.length() >= 8;
	}

	public static void assertValid(String password) throws IllegalKnowyPasswordException {
		ASSERT_IS_VALID_PASSWORD
			.orElseThrow(() -> new IllegalKnowyPasswordException(VALIDATION_ERROR_MESSAGE))
			.value(password);
	}

	public static void assertEquals(Password expected, Password candidate) throws IllegalKnowyPasswordException {
		PASSWORD_COMPARATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Password expected, Password candidate) {
		return PASSWORD_COMPARATOR.equals(expected, candidate);
	}

	public static void assertEquals(Password expected, String candidate) throws IllegalKnowyPasswordException {
		PASSWORD_COMPARATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Password expected, String candidate) {
		return PASSWORD_COMPARATOR.equals(expected, candidate);
	}

	public static void assertEquals(String expected, String candidate) throws IllegalKnowyPasswordException {
		PASSWORD_COMPARATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(String expected, String candidate) {
		return PASSWORD_COMPARATOR.equals(expected, candidate);
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
