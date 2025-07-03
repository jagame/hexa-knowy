package com.knowy.server.application.service.usecase.recovery;

import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.KnowySecurityException;
import com.knowy.server.application.port.gateway.MessageDispatcher;
import com.knowy.server.application.port.gateway.MessageDispatcher.KnowyMessageDispatchException;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.port.security.TokenMapper.KnowyTokenGenerationException;
import com.knowy.server.application.service.exception.AccessException;
import com.knowy.server.application.service.usecase.KnowyUseCase;
import com.knowy.server.util.exception.UserNotFoundException;

import java.net.URI;

public class SendPasswordRecoveryMessageUseCase implements KnowyUseCase<SendPasswordRecoveryMessageCommand, Void> {

	private final PrivateUserRepository privateUserRepository;
	private final TokenMapper tokenMapper;
	private final MessageDispatcher messageDispatcher;

	public SendPasswordRecoveryMessageUseCase(
		PrivateUserRepository privateUserRepository,
		TokenMapper tokenMapper,
		MessageDispatcher messageDispatcher
	) {
		this.privateUserRepository = privateUserRepository;
		this.tokenMapper = tokenMapper;
		this.messageDispatcher = messageDispatcher;
	}

	@Override
	public Void execute(SendPasswordRecoveryMessageCommand param) throws KnowySecurityException, KnowyPersistenceException {
		try {
			PrivateUser user = privateUserRepository.findByEmail(param.userEmail())
				.orElseThrow(() -> new UserNotFoundException(
					"The user with email %s was not found".formatted(param.userEmail().value())
				));

			String token = tokenMapper.generate(user);
			String messageBody = buildMessageBody(param.passwordRecoveryUri(), token);
			messageDispatcher.sendMessage(user, "password recovery", messageBody);

			return null;
		} catch (UserNotFoundException | KnowyTokenGenerationException | KnowyMessageDispatchException e) {
			throw new AccessException("Failed to send the message of password reset to the user", e);
		}
	}

	private String buildMessageBody(URI recoveryUri, String token) {
		String url = "%s?token=%s".formatted(recoveryUri.toString(), token);
		return """
			¡Hola, talentoso %%$@€#&%%$%%! 👋
			
			Sabemos que tu camino como $%%$@%%&€#@&$ es importante, por eso te ayudamos a recuperar tu acceso. \s
			Haz clic en el siguiente enlace para restablecer tu contraseña:
			
			%s
			
			Este enlace es válido solo por un tiempo limitado.
			Si no fuiste tú quien pidió este cambio, no te preocupes, simplemente ignora este correo.
			
			¡Sigue aprendiendo y conquistando tus metas con Knowy! 💪
			
			---
			© 2025 KNOWY, Inc
			""".formatted(url);
	}
}
