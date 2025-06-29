package com.knowy.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.knowy.server.util.exception.JsonException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

	@Value("${knowy.jwt.key}")
	private String secretKey;
	private SecretKey key;

	private final JsonService jsonService;

	public JwtService(JsonService jsonService) {
		this.jsonService = jsonService;
	}

	@PostConstruct
	private void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	public <T> String encode(T obj) {
		try {
			return Jwts.builder()
				.claim("obj", obj)
				.signWith(key)
				.expiration(new Date(System.currentTimeMillis() + 3_600_000))
				.compact();
		} catch (JwtException e) {
			throw new JwtException("Failed to encode claims", e);
		}
	}

	public <T> T decode(String token, Class<T> clazz) {
		try {
			JwtParser parser = Jwts.parser()
				.verifyWith(key)
				.build();

			Map objJson = parser.parseSignedClaims(token).getPayload().get("obj", Map.class);
			return jsonService.fromJson(objJson, clazz);
		}  catch (JwtException | JsonProcessingException e) {
			throw new JwtException("Failed to decode claims", e);
		}
	}

	public String generatePasswordResetToken(String email, int userId) {
		// TODO - Implementar JWT
		return UUID.randomUUID().toString();
	}

	public String createLoginToken(String email, int userId) {
		// TODO - Reemplazar por JWT más adelante
		return UUID.randomUUID().toString();
	}
}
