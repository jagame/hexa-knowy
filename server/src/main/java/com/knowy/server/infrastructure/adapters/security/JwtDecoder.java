package com.knowy.server.infrastructure.adapters.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;

import javax.crypto.SecretKey;
import java.util.Map;

class JwtDecoder<T> {

	private final Class<T> dataClass;
	private final JwtParser jwtParser;

	JwtDecoder(SecretKey key, Class<T> dataClass) {
		this.dataClass = dataClass;
		this.jwtParser = Jwts.parser()
			.verifyWith(key)
			.json(new JacksonDeserializer<>(Map.of("data", dataClass)))
			.build();
	}

	JwtDecoder(Class<T> dataClass) {
		this.dataClass = dataClass;
		this.jwtParser = Jwts.parser()
			.build();
	}

	Claims parseToken(String token) {
		return jwtParser
			.parseSignedClaims(token)
			.getPayload();
	}

	T extractDataClaim(Claims claims) {
		return claims.get("data", dataClass);
	}

}
