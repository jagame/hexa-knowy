package com.knowy.server.application.domain;

import com.knowy.server.application.domain.assertion.Assert;
import com.knowy.server.application.domain.assertion.ValueObjectComparator;
import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.InvalidKnowyEmailFormatException;

import java.util.Objects;

public record Email(String value) implements ValueObject<String> {

	private static final String VALIDATION_ERROR_MESSAGE = "An email must match the expression <user>@<domain>";
	private static final Assert.AssertionBuilder<String> ASSERT_IS_VALID_EMAIL = Assert.that(Email::isValid);
	private static final ValueObjectComparator<String, Email, IllegalKnowyEmailException> EMAIL_COMPARATOR =
		new ValueObjectComparator<>(
			"email",
			IllegalKnowyEmailException::new
		);

	public Email {
		Objects.requireNonNull(value, "An email value can't be null");
		ASSERT_IS_VALID_EMAIL
			.orElseThrow(() -> new InvalidKnowyEmailFormatException(VALIDATION_ERROR_MESSAGE))
			.value(value);
	}

	public static boolean isValid(String emailValue) {
		return emailValue != null && emailValue.chars()
			.filter(ch -> ch == '@')
			.count() == 1;
	}

	public static void assertValid(String email) throws IllegalKnowyEmailException {
		ASSERT_IS_VALID_EMAIL
			.orElseThrow(() -> new IllegalKnowyEmailException(VALIDATION_ERROR_MESSAGE))
			.value(email);
	}

	public static void assertEquals(Email expected, Email candidate) throws IllegalKnowyEmailException {
		EMAIL_COMPARATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Email expected, Email candidate) {
		return EMAIL_COMPARATOR.equals(expected, candidate);
	}

	public static void assertEquals(Email expected, String candidate) throws IllegalKnowyEmailException {
		EMAIL_COMPARATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Email expected, String candidate) {
		return EMAIL_COMPARATOR.equals(expected, candidate);
	}

	public static void assertEquals(String expected, String candidate) throws IllegalKnowyEmailException {
		EMAIL_COMPARATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(String expected, String candidate) {
		return EMAIL_COMPARATOR.equals(expected, candidate);
	}

	public void assertEquals(Email other) throws IllegalKnowyEmailException {
		assertEquals(this, other);
	}

	public void assertEquals(String other) throws IllegalKnowyEmailException {
		assertEquals(this, other);
	}

	@Override
	public boolean hasValue(String expected) {
		return equals(this.value(), expected);
	}
}
