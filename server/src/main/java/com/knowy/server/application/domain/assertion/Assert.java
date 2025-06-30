package com.knowy.server.application.domain.assertion;

import java.util.function.*;

public final class Assert {

	private Assert() {
	}

	public static <E extends Throwable> void supplier(BooleanSupplier assertion, Supplier<E> exceptionSupplier) throws E {
		if (!assertion.getAsBoolean()) {
			throw exceptionSupplier.get();
		}
	}

	public static <T, E extends Throwable> void predicate(
		T value, Predicate<T> predicate, Function<T, E> exceptionMapper
	) throws E {
		if (!predicate.test(value)) {
			throw exceptionMapper.apply(value);
		}
	}

	public static <T, U, E extends Throwable> void biPredicate(
		T oneValue, U otherValue, BiPredicate<T, U> biPredicate, BiFunction<T, U, E> exceptionMapper
	) throws E {
		if (!biPredicate.test(oneValue, otherValue)) {
			throw exceptionMapper.apply(oneValue, otherValue);
		}
	}

	public static <E extends Throwable> GenericAssertionBuilder<E> orElseThrow(Supplier<E> exceptionSupplier) {
		return new GenericAssertionBuilder<>(exceptionSupplier);
	}

	public static <T> AssertionBuilder<T> that(Predicate<T> predicate) {
		return new AssertionBuilder<>(predicate);
	}

	public static <T, U> BiAssertionBuilder<T, U> that(BiPredicate<T, U> bipredicate) {
		return new BiAssertionBuilder<>(bipredicate);
	}

	public static class GenericAssertionBuilder<E extends Throwable> {

		private final Supplier<E> exceptionSupplier;

		public GenericAssertionBuilder(Supplier<E> exceptionSupplier) {
			this.exceptionSupplier = exceptionSupplier;
		}

		public void that(BooleanSupplier supplier) throws E {
			Assert.supplier(supplier, exceptionSupplier);
		}

		public <T> Assertion<T, E> that(Predicate<T> predicate) {
			return new Assertion<>(predicate, (o1) -> exceptionSupplier.get());
		}

		public <T, U> BiAssertion<T, U, E> that(BiPredicate<T, U> bipredicate) {
			return new BiAssertion<>(bipredicate, (o1, o2) -> exceptionSupplier.get());
		}
	}

	public static class AssertionBuilder<T> {

		private final Predicate<T> predicate;

		AssertionBuilder(Predicate<T> predicate) {
			this.predicate = predicate;
		}

		public <E extends Throwable> Assertion<T, E> orElseThrow(Function<T, E> exceptionMapper) {
			return new Assertion<>(predicate, exceptionMapper);
		}

		public <E extends Throwable> Assertion<T, E> orElseThrow(Supplier<E> exceptionMapper) {
			return new Assertion<>(predicate, o1 -> exceptionMapper.get());
		}

	}

	public static class Assertion<T, E extends Throwable> {

		private final Predicate<T> predicate;
		private final Function<T, E> exceptionMapper;

		public Assertion(Predicate<T> predicate, Function<T, E> exceptionMapper) {
			this.predicate = predicate;
			this.exceptionMapper = exceptionMapper;
		}

		public void value(T value) throws E {
			Assert.predicate(value, predicate, exceptionMapper);
		}

	}

	public static class BiAssertionBuilder<T, U> {

		private final BiPredicate<T, U> biPredicate;

		BiAssertionBuilder(BiPredicate<T, U> biPredicate) {
			this.biPredicate = biPredicate;
		}

		public <E extends Throwable> BiAssertion<T, U, E> orElseThrow(BiFunction<T, U, E> exceptionMapper) {
			return new BiAssertion<>(biPredicate, exceptionMapper);
		}

		public <E extends Throwable> BiAssertion<T, U, E> orElseThrow(Supplier<E> exceptionMapper) {
			return new BiAssertion<>(biPredicate, (o1, o2) -> exceptionMapper.get());
		}


	}

	public static class BiAssertion<T, U, E extends Throwable> {

		private final BiPredicate<T, U> biPredicate;
		private final BiFunction<T, U, E> exceptionMapper;

		public BiAssertion(BiPredicate<T, U> biPredicate, BiFunction<T, U, E> exceptionMapper) {
			this.biPredicate = biPredicate;
			this.exceptionMapper = exceptionMapper;
		}

		public void values(T oneValue, U otherValue) throws E {
			Assert.biPredicate(oneValue, otherValue, biPredicate, exceptionMapper);
		}

	}

}
