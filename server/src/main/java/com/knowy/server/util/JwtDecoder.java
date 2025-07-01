package com.knowy.server.util;

import com.knowy.server.util.exception.JwtKnowyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;

import javax.crypto.SecretKey;
import java.util.Map;

public class JwtDecoder<T> {

	private final Class<T> dataClass;
	private final JwtParser jwtParser;

	public JwtDecoder(SecretKey key, Class<T> dataClass) {
		this.dataClass = dataClass;
		this.jwtParser = Jwts.parser()
			.verifyWith(key)
			.json(new JacksonDeserializer<>(Map.of("data", dataClass)))
			.build();
	}

	public JwtDecoder(Class<T> dataClass) {
		this.dataClass = dataClass;
		this.jwtParser = Jwts.parser()
			.build();
	}

	public Claims parseToken(String token) {
		return jwtParser
			.parseSignedClaims(token)
			.getPayload();
	}

	public T extractDataClaim(Claims claims) {
		return claims.get("data", dataClass);
	}

}
