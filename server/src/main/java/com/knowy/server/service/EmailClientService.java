package com.knowy.server.service;

import com.knowy.server.service.exception.MailDispatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailClientService {

	private final JavaMailSender mailSender;

	public EmailClientService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendTokenToEmail(String token, String email) throws MailDispatchException {
		String subject = "Tu código de verificación KNOWY";
		String body = tokenBody(token);

		sendEmail(email, subject, body);
	}

	private String tokenBody(String token) {
		return """
			Hola,
			
			Tu código de verificación es: %s
			
			Si no solicitaste este código, puedes ignorar este correo.
			
			---
			© 2025 KNOWY, Inc
			Términos: https://knowy.com/terminos
			""".formatted(token);
	}

	public void sendEmail(String to, String subject, String body) throws MailDispatchException {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("knowy-learn@knowy.com");
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);

			mailSender.send(message);
		} catch (MailException e) {
			throw new MailDispatchException("Fail to send email to " + to, e);
		}
	}
}
