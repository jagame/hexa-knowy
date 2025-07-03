package com.knowy.server.application.port.gateway;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.PublicUser;

public interface MessageDispatcher {

	default void sendMessage(PublicUser to, String message) throws KnowyMessageDispatchException {
		sendMessage(to, null, message);
	}

	void sendMessage(PublicUser to, String subject, String message) throws KnowyMessageDispatchException;

	void sendMessage(Email to, String subject, String messageBody) throws KnowyMessageDispatchException;

	class KnowyMessageDispatchException extends KnowyDispatchException {

		public KnowyMessageDispatchException(String message, Throwable cause) {
			super(message, cause);
		}

		public KnowyMessageDispatchException(Throwable cause) {
			super(cause);
		}
	}

}
