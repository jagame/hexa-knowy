package com.knowy.server.infrastructure.adapter.gateway;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.application.port.gateway.MessageDispatcher;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class UserEmailSender implements MessageDispatcher {

	private final JavaMailSender mailSender;
	private final PrivateUserRepository privateUserRepository;

	public UserEmailSender(JavaMailSender mailSender, PrivateUserRepository privateUserRepository) {
		this.mailSender = mailSender;
		this.privateUserRepository = privateUserRepository;
	}

	@Override
	public void sendMessage(PublicUser to, String messageBody) throws KnowyMessageDispatchException {
		MessageDispatcher.super.sendMessage(to, messageBody);
	}

	@Override
	public void sendMessage(PublicUser to, String subject, String messageBody) throws KnowyMessageDispatchException {
		try {
			Email userEmailAddress = findEmailAddress(to);
			sendMessage(userEmailAddress, subject, messageBody);
		} catch (KnowyPersistenceException e) {
			throw new KnowyMessageDispatchException(e);
		}
	}

	@Override
	public void sendMessage(Email to, String subject, String messageBody) throws KnowyMessageDispatchException {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("knowy-learn@knowy.com");
			message.setTo(to.value());
			message.setSubject(subject);
			message.setText(messageBody);

			mailSender.send(message);
		} catch (MailException e) {
			throw new KnowyMessageDispatchException("Fail to send email to " + to, e);
		}
	}

	private Email findEmailAddress(PublicUser publicUser) throws KnowyPersistenceException {
		if (publicUser instanceof PrivateUser user) {
			return user.email();
		}
		return privateUserRepository.findById(publicUser.id())
			.map(PrivateUser::email)
			.orElseThrow(() -> new IllegalStateException(
					"No private data was found for the user with id %d".formatted(publicUser.id())
			));
	}
}
