package com.knowy.server.util;

import com.knowy.server.util.exception.MailDispatchException;
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
