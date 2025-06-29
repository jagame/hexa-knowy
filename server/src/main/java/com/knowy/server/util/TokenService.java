package com.knowy.server.util;

import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.UUID;

@Service
public class TokenService {

/*	private String secretKey;

	private Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));*/

	public String generatePasswordResetToken(String email, int userId) {
		// TODO - Implementar JWT
		return UUID.randomUUID().toString();
	}

	public String createLoginToken(String email, int userId) {
		// TODO - Reemplazar por JWT más adelante
		return UUID.randomUUID().toString();
	}
}
