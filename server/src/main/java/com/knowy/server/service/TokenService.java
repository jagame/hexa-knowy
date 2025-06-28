package com.knowy.server.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {

	public String createPasswordResetToken(String email, Long userId) {
		// TODO - Implementar JWT
		return UUID.randomUUID().toString();
	}

	public String createLoginToken(String email, Long userId) {
		// TODO - Reemplazar por JWT más adelante
		return UUID.randomUUID().toString();
	}
}
