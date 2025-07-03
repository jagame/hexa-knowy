package com.knowy.server.infrastructure.adapter.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.util.JwtDecoder;
import com.knowy.server.util.exception.JwtKnowyException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtTokenMapper implements TokenMapper {

	private final PrivateUserRepository privateUserRepository;
	private final SecretKey systemKey;
	private final ObjectMapper jsonObjectMapper;

	public JwtTokenMapper(
		@Value("${spring.jwt.key}") String systemKey,
		PrivateUserRepository privateUserRepository,
		ObjectMapper jsonObjectMapper
	) {
		this.systemKey = Keys.hmacShaKeyFor(systemKey.getBytes(StandardCharsets.UTF_8));;
		this.privateUserRepository = privateUserRepository;
		this.jsonObjectMapper = jsonObjectMapper;
	}

	@Override
	public String generate(PrivateUser user) throws KnowyTokenGenerationException {
		try {
			SecretKey superSecretKey = keyCalculator(user.password().value());
			return Jwts.builder()
				.subject(String.valueOf(user.id()))
				.signWith(superSecretKey)
				.expiration(new Date(System.currentTimeMillis() + 3_600_000))
				.compact();
		} catch (JwtException | JwtKnowyException e) {
			throw new KnowyTokenGenerationException("Failed to encode claims", e);
		}
	}

	private SecretKey keyCalculator(String secondaryKey) throws JwtKnowyException {
		try {
			Mac mac = Mac.getInstance("HmacSHA256");
			mac.init(systemKey);
			byte[] secondaryKeySign = mac.doFinal(
				secondaryKey.getBytes(StandardCharsets.UTF_8)
			);
			return Keys.hmacShaKeyFor(secondaryKeySign);
		} catch (NoSuchAlgorithmException | InvalidKeyException e) {
			throw new JwtKnowyException("Failed to encrypt token", e);
		}
	}

	@Override
	public boolean isValid(String token) {
		try {
			translate(token);
			return true;
		} catch (KnowyTokenTranslationException e) {
			return false;
		}
	}

	@Override
	public PrivateUser translate(String token) throws KnowyTokenTranslationException {
		try {
			PrivateUser user = translateUnverified(token)
				.orElseThrow(() ->
					new KnowyTokenTranslationException("There is no valid user associated with the specified token")
				);

			assertTokenValidation(token, user.password());
			return user;
		} catch (JwtKnowyException e) {
			throw new KnowyTokenTranslationException("Failed to decode claims", e);
		}
	}

	private void assertTokenValidation(String token, Password userPassword) throws JwtKnowyException {
		SecretKey superSecretKey = keyCalculator(userPassword.value());
		new JwtDecoder(superSecretKey)
			.verify(token);
	}

	@Override
	public Optional<PrivateUser> translateUnverified(String token) throws KnowyTokenTranslationException {
		try {
			String payloadJson = extractPayloadJson(token);
			String userIdText = jsonObjectMapper.readTree(payloadJson)
				.get("sub")
				.asText();
			int userId = Integer.parseInt(userIdText);
			return privateUserRepository.findById(userId);
		} catch (JsonProcessingException | NumberFormatException | KnowyPersistenceException | JwtKnowyException e) {
			throw new KnowyTokenTranslationException("Failed to decode claims", e);
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
}
