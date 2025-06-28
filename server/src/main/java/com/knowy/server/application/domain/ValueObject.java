package com.knowy.server.application.domain;

public interface ValueObject<V> {

	V value();

	boolean hasValue(V value);

}
