package com.knowy.server.application.domain;

import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.InvalidKnowyEmailFormatException;
import com.knowy.server.application.domain.validation.GenericValidator;

import java.util.Objects;

public record Email(String value) implements ValueObject<String> {

	private static final GenericValidator<String, Email, IllegalKnowyEmailException> EMAIL_VALIDATOR =
		new GenericValidator<>(
			"email",
			"An email must match the expression <user>@<domain>",
			emailValue -> emailValue != null && emailValue.chars()
				.filter(ch -> ch == '@')
				.count() == 1,
			IllegalKnowyEmailException::new
		);

	public Email {
		Objects.requireNonNull(value, "An email value can't be null");
		if (!isValid(value)) {
			throw new InvalidKnowyEmailFormatException("An email must match the expression <user>@<domain>");
		}
	}

	public static void assertValid(String email) throws IllegalKnowyEmailException {
		EMAIL_VALIDATOR.assertValid(email);
	}

	public static boolean isValid(String emailValue) {
		return EMAIL_VALIDATOR.isValid(emailValue);
	}

	public static void assertEquals(Email expected, Email candidate) throws IllegalKnowyEmailException {
		EMAIL_VALIDATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Email expected, Email candidate) {
		return EMAIL_VALIDATOR.equals(expected, candidate);
	}

	public static void assertEquals(Email expected, String candidate) throws IllegalKnowyEmailException {
		EMAIL_VALIDATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(Email expected, String candidate) {
		return EMAIL_VALIDATOR.equals(expected, candidate);
	}

	public static void assertEquals(String expected, String candidate) throws IllegalKnowyPasswordException {
		EMAIL_VALIDATOR.assertEquals(expected, candidate);
	}

	public static boolean equals(String expected, String candidate) {
		return EMAIL_VALIDATOR.equals(expected, candidate);
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
