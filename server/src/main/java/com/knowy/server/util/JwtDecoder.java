package com.knowy.server.util;

import com.knowy.server.util.exception.JwtKnowyException;
import io.jsonwebtoken.*;

import javax.crypto.SecretKey;

public class JwtDecoder {

	private final JwtParser jwtParser;

	public JwtDecoder(SecretKey key) {
		this.jwtParser = Jwts.parser()
			.verifyWith(key)
			.build();
	}

	public Claims parseToken(String token) throws JwtKnowyException {
		return verify(token)
			.getPayload();
	}

	/**
	 * @param token the token to verify
	 * @return The jws result of parse the token, when valid
	 * @throws JwtKnowyException if the jwt string cannot be parsed or validated as required.
	 */
	public Jws<Claims> verify(String token) throws JwtKnowyException {
		try {
			return jwtParser
				.parseSignedClaims(token);
		} catch (JwtException e) {
			throw new JwtKnowyException("Invalid token", e);
		}
	}

}
