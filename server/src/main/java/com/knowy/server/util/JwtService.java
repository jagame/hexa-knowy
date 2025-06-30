package com.knowy.server.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowy.server.util.exception.JwtKnowyException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

	private final SecretKey key;
	private final ObjectMapper objectMapper;

	public JwtService(@Value("${spring.jwt.key}") String secretKey, ObjectMapper objectMapper) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.objectMapper = objectMapper;
	}

	/**
	 * Encodes the given object into a signed JWT string.
	 *
	 * <p>This method serializes the provided object and places it under the {@code "data"} claim in the JWT payload.
	 * The token is then signed using a derived HMAC key, which is calculated by combining:
	 * <ul>
	 *   <li>The user's encrypted password (acting as a user-specific secret), and</li>
	 *   <li>A secure server-side secret key (unique to the application environment).</li>
	 * </ul>
	 * This composition ensures that the token is tightly bound to both the user and the server, offering protection
	 * against token forgery even if one of the keys is compromised.
	 *
	 * <p>The generated token is set to expire after 1 hour (3600000 ms) by default.
	 *
	 * @param obj          the object to encode into the JWT payload, typically containing relevant security context
	 *                     (e.g., user ID, email, token type, etc.)
	 * @param secondaryKey a user-specific component (usually an encrypted password) used to derive the signing key
	 * @param <T>          the type of the object being encoded
	 * @return a compact JWT string containing the signed and encoded data
	 * @throws JwtKnowyException if the token generation fails due to signing or serialization issues
	 */
	public <T> String encode(T obj, String secondaryKey) throws JwtKnowyException {
		try {
			SecretKey superSecretKey = keyCalculator(secondaryKey);
			return Jwts.builder()
				.claim("data", obj)
				.signWith(superSecretKey)
				.expiration(new Date(System.currentTimeMillis() + 3_600_000))
				.compact();
		} catch (JwtException | JwtKnowyException e) {
			throw new JwtKnowyException("Failed to encode claims", e);
		}
	}

	/**
	 * Decodes and verifies a signed JWT token using a dynamically derived HMAC key,
	 * and deserializes the {@code "data"} claim into an instance of the specified class.
	 *
	 * <p>The verification key (superkey) is derived by applying HMAC-SHA256 to the provided
	 * {@code secondaryKey} (e.g., the user’s encrypted password), using a server-side base secret key.
	 * This ensures that:
	 * <ul>
	 *   <li>The token is tied both to the user and the server environment.</li>
	 *   <li>It cannot be verified or regenerated without both keys being correct.</li>
	 * </ul>
	 *
	 * <p>After verifying the token's signature, this method extracts the {@code "data"} field from
	 * the payload and deserializes it into the specified class.
	 *
	 * @param secondaryKey the user-specific secondary key used to derive the HMAC verification key
	 * @param token        the signed JWT token to decode and verify
	 * @param clazz        the class to deserialize the {@code "data"} claim into
	 * @param <T>          the type of the deserialized object
	 * @return the deserialized object from the {@code "data"} claim
	 * @throws JwtKnowyException if the token is invalid, the signature cannot be verified, or deserialization fails
	 */
	public <T> T decode(String secondaryKey, String token, Class<T> clazz) throws JwtKnowyException {
		try {
			SecretKey superSecretKey = keyCalculator(secondaryKey);

			var jwtDecoder = new JwtDecoder<>(superSecretKey, clazz);
			Claims claims = jwtDecoder.parseToken(token);
			return jwtDecoder.extractDataClaim(claims);
		} catch (JwtException | JwtKnowyException e) {
			throw new JwtKnowyException("Failed to decode claims", e);
		}
	}

	private SecretKey keyCalculator(String secondaryKey) throws JwtKnowyException {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(key);
			byte[] secondaryKeySign = mac.doFinal(
				secondaryKey.getBytes(StandardCharsets.UTF_8)
			);
			return Keys.hmacShaKeyFor(secondaryKeySign);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new JwtKnowyException("Failed to encrypt token", e);
		}
	}

	/**
	 * Decodes a JWT token *without verifying its signature* and extracts the "data" claim
	 * from the payload, mapping it to an instance of the specified class.
	 *
	 * <p><strong>⚠️ Warning:</strong> This method does <em>not</em> verify the JWT's signature.
	 * Use it only in trusted environments or when the token has already been verified through other means.</p>
	 *
	 * <p>The method expects the JWT payload to be a JSON object that includes a top-level "data" field,
	 * which itself is a JSON object representing the domain-specific content.</p>
	 *
	 * @param token the JWT token string (Base64-encoded header.payload.signature)
	 * @param clazz the target class to map the "data" field to
	 * @param <T>   the type to deserialize the "data" object into
	 * @return an instance of the specified class representing the "data" claim
	 * @throws JwtKnowyException if the token is invalid, improperly formatted,
	 *                           or if deserialization of the "data" field fails
	 */
	public <T> T decodeUnverified(String token, Class<T> clazz) throws JwtKnowyException {
		try {
			String payloadJson = extractPayloadJson(token);
			JsonNode jsonData = extractDataNode(payloadJson);
			return jsonDataToClass(jsonData, clazz);
		} catch (JwtException | JsonProcessingException e) {
			throw new JwtKnowyException("Failed to decode claims", e);
		}
	}

	private String extractPayloadJson(String token) throws JwtKnowyException {
		String[] parts = getTokenParts(token);
		return new String(Base64.getDecoder().decode(parts[1]), StandardCharsets.UTF_8);
	}

	private String[] getTokenParts(String token) throws JwtKnowyException {
		String[] parts = token.split("\\.");
		if (parts.length != 3) {
			throw new JwtKnowyException("Invalid JWT token format");
		}
		return parts;
	}

	private JsonNode extractDataNode(String payloadJson) throws JsonProcessingException {
		return objectMapper.readTree(payloadJson).get("data");
	}

	private <T> T jsonDataToClass(JsonNode jsonData, Class<T> clazz) throws JsonProcessingException {
		return objectMapper.treeToValue(jsonData, clazz);
	}
}
