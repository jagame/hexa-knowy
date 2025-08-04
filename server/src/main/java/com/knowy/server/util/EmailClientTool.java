package com.knowy.server.util;

import com.knowy.server.application.exception.KnowyMailDispatchException;
import com.knowy.server.application.ports.KnowyEmailClientTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailClientTool implements KnowyEmailClientTool {

	private final JavaMailSender mailSender;

	public EmailClientTool(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Override
	public void sendEmail(String to, String subject, String body) throws KnowyMailDispatchException {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("knowy-learn@knowy.com");
			message.setTo(to);
			message.setSubject(subject);
			message.setText(body);

			mailSender.send(message);
		} catch (MailException e) {
			throw new KnowyMailDispatchException("Fail to send email to " + to, e);
		}
	}
}
