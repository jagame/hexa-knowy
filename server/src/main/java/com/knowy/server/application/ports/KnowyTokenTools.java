package com.knowy.server.application.ports;

import com.knowy.server.application.exception.KnowyTokenException;

public interface KnowyTokenTools {
	<T> String encode(T obj, String secondaryKey, long tokenExpirationTime) throws KnowyTokenException;

	<T> String encode(T obj, String secondaryKey) throws KnowyTokenException;

	<T> T decode(String secondaryKey, String token, Class<T> clazz) throws KnowyTokenException;

	<T> T decodeUnverified(String token, Class<T> clazz) throws KnowyTokenException;
}
