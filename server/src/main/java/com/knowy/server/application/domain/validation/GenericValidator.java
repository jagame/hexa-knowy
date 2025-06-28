package com.knowy.server.application.domain.validation;

import com.knowy.server.application.domain.ValueObject;
import com.knowy.server.application.domain.error.IllegalKnowyDataException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class GenericValidator<V, T extends ValueObject<V>, E extends IllegalKnowyDataException> {

	private final Predicate<V> validation;
	private final String dataName;
	private final String validationErrorMessage;
	private final Function<String, E> exceptionFactory;

	public GenericValidator(
		String dataName,
		String validationErrorMessage,
		Predicate<V> validation,
		Function<String, E> exceptionFactory
	) {
		this.dataName = dataName;
		this.validation = validation;
		this.validationErrorMessage = validationErrorMessage;
		this.exceptionFactory = exceptionFactory;
	}

	public void assertValid(V value) throws E {
		if (!isValid(value)) {
			throw exceptionFactory.apply(validationErrorMessage);
		}
	}

	public boolean isValid(V value) {
		return validation.test(value);
	}

	public void assertEquals(T expected, T candidate) throws E {
		if (!equals(expected, candidate)) {
			throw exceptionFactory.apply(
				"The checked " + dataName + " doesn't match the value of the expected one"
			);
		}
	}

	public boolean equals(T expected, T candidate) {
		Objects.requireNonNull(expected, "The expected " + dataName + " can't be null");
		return Objects.equals(expected, candidate);
	}

	public void assertEquals(T expected, V candidate) throws E {
		if (!equals(expected, candidate)) {
			throw exceptionFactory.apply(
				"The checked " + dataName + " doesn't match the value of the expected one"
			);
		}
	}

	public boolean equals(T expected, V candidate) {
		Objects.requireNonNull(expected, "You need a non null " + dataName + " if you want to check another one");
		return equals(expected.value(), candidate);
	}

	public void assertEquals(V expected, V candidate) throws IllegalKnowyPasswordException {
		if (!equals(expected, candidate)) {
			throw new IllegalKnowyPasswordException(
				"The checked " + dataName + " doesn't match with the expected one"
			);
		}
	}

	public boolean equals(V expected, V candidate) {
		Objects.requireNonNull(expected, "You need a non null " + dataName + " if you want to check another one");
		return Objects.equals(expected, candidate);
	}

}
