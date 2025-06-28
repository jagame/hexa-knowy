package com.knowy.server.application.port.gateway;

import com.knowy.server.application.domain.PublicUser;

public interface MessageGateway {

	default void sendMessage(PublicUser to, String message) throws KnowyMessageDispatchException {
		sendMessage(to, null, message);
	}

	void sendMessage(PublicUser to, String subject, String message) throws KnowyMessageDispatchException;

	class KnowyMessageDispatchException extends KnowyDispatchException {

		public KnowyMessageDispatchException(String message, Throwable cause) {
			super(message, cause);
		}

		public KnowyMessageDispatchException(Throwable cause) {
			super(cause);
		}
	}

}
