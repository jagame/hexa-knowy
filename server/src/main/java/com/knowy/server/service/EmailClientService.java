package com.knowy.server.service;

import org.springframework.stereotype.Service;

@Service
public class EmailClientService {

	public void sendTokenToEmail(String token, String email) {
		System.out.println("Token: " + token);
	}
}
