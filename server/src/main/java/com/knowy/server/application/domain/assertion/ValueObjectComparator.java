package com.knowy.server.application.domain.assertion;

import com.knowy.server.application.domain.ValueObject;
import com.knowy.server.application.domain.error.IllegalKnowyDataException;

import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;

public class ValueObjectComparator<V extends Comparable<V>, T extends ValueObject<V>,
	E extends IllegalKnowyDataException>
	implements Comparator<T>
{

	private static final String VALUE_COMPARE_ERROR_MSG = "The checked %s doesn't match with the expected one";
	private static final String UNEXPECTED_NULL_ERROR_MSG = "You need a non null %s if you want to check another one";

	private final String dataName;
	private final Assert.GenericAssertionBuilder<E> assertElseThrowError;

	public ValueObjectComparator(
		String dataName,
		Function<String, E> exceptionFactory
	) {
		this.dataName = dataName;
		this.assertElseThrowError = Assert.orElseThrow(() ->
			exceptionFactory.apply(String.format(VALUE_COMPARE_ERROR_MSG, dataName))
		);
	}

	public void assertEquals(T expected, T candidate) throws E {
		assertElseThrowError
			.<T, T>that(this::equals)
			.values(expected, candidate);
	}

	public boolean equals(T expected, T candidate) {
		assertExpectedNotNull(expected);
		return Objects.equals(expected, candidate);
	}

	@Override
	public int compare(T oneValueObj, T otherValueObj) {
		if(otherValueObj == null) {
			return -1;
		}
		return compare(oneValueObj, otherValueObj.value());
	}

	public void assertEquals(T expected, V candidate) throws E {
		assertElseThrowError
			.<T, V>that(this::equals)
			.values(expected, candidate);
	}

	public boolean equals(T expected, V candidate) {
		assertExpectedNotNull(expected);
		return equals(expected.value(), candidate);
	}

	public int compare(T oneValueObj, V otherValue) {
		assertExpectedNotNull(oneValueObj);
		return compare(oneValueObj.value(), otherValue);
	}

	public void assertEquals(V expected, V candidate) throws E {
		assertElseThrowError
			.<V, V>that(this::equals)
			.values(expected, candidate);
	}

	public boolean equals(V expected, V candidate) {
		return compare(expected, candidate) == 0;
	}

	public int compare(V oneValue, V otherValue) {
		assertExpectedNotNull(oneValue);
		return oneValue.compareTo(otherValue);
	}

	private void assertExpectedNotNull(Object expected) {
		Objects.requireNonNull(expected, String.format(UNEXPECTED_NULL_ERROR_MSG, dataName));
	}
}
