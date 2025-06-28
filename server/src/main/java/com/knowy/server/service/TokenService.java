package com.knowy.server.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

	public String generatePasswordResetToken(String email, int userId) {
		// TODO - Implementar JWT
		return UUID.randomUUID().toString();
	}

	public String createLoginToken(String email, int userId) {
		// TODO - Reemplazar por JWT más adelante
		return UUID.randomUUID().toString();
	}
}
