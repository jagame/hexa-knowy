package com.knowy.server.service;

import org.springframework.stereotype.Service;

@Service
public class EmailClientService {

	public void sendTokenToEmail(String token, String email) {
		// TODO - Implementar servicio de correo
		System.out.println("Token: " + token);
	}
}
