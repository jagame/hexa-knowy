package com.knowy.server.util;

import com.knowy.server.util.exception.JsonKnowyException;
import com.knowy.server.util.exception.JwtKnowyException;
import io.jsonwebtoken.*;
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

	private final JsonSerializationService jsonSerializationService;

	public JwtService(JsonSerializationService jsonSerializationService) {
		this.jsonSerializationService = jsonSerializationService;
	}

	/**
	 * Initializes the HMAC signing key using the configured secret key.
	 */
	@PostConstruct
	private void init() {
		key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Encodes the given object as a signed JWT string.
	 * <p>
	 * The object is added to the token's claims under the key {@code "data"},
	 * and the token is signed using the initialized HMAC key.
	 * The generated token has a default expiration time of 1 hour.
	 *
	 * @param obj the object to encode into the JWT payload
	 * @param <T> the type of the object being encoded
	 * @return a compact JWT string containing the encoded and signed data
	 * @throws JwtKnowyException if the token generation fails
	 */
	public <T> String encode(T obj) {
		try {
			return Jwts.builder()
				.claim("data", obj)
				.signWith(key)
				.expiration(new Date(System.currentTimeMillis() + 3_600_000))
				.compact();
		} catch (JwtException e) {
			throw new JwtKnowyException("Failed to encode claims", e);
		}
	}

	/**
	 * Decodes the given JWT token and extracts the embedded object of the specified type.
	 * <p>
	 * The method verifies the token's signature using the pre-configured key, parses the payload,
	 * and retrieves the {@code "data"} claim, which is expected to contain the serialized object.
	 * The data is then converted into the target class using the application's JSON serialization service.
	 *
	 * @param token the JWT token to decode
	 * @param clazz the target class to convert the embedded data into
	 * @param <T> the type of the object expected from the token
	 * @return the object extracted from the JWT payload
	 * @throws JwtKnowyException if the token is invalid or if deserialization fails
	 */
	public <T> T decode(String token, Class<T> clazz) {
		try {
			JwtParser parser = Jwts.parser()
				.verifyWith(key)
				.build();

			Claims claims = parser.parseSignedClaims(token).getPayload();
			Map<?, ?> objJson = claims.get("data", Map.class);

			return jsonSerializationService.fromJson(objJson, clazz);
		} catch (JwtException | JsonKnowyException e) {
			throw new JwtKnowyException("Failed to decode claims", e);
		}
	}

	public String generatePasswordResetToken(String email, int userId) {
		// TODO - Borrar
		return UUID.randomUUID().toString();
	}

	public String createLoginToken(String email, int userId) {
		// TODO - Reemplazar por JWT más adelante
		return UUID.randomUUID().toString();
	}
}
