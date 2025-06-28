package com.knowy.server.application.port.security;

import com.knowy.server.application.domain.PublicUser;

public interface TokenMapper {

	String generate(PublicUser user);

	PublicUser translate(String token) throws KnowyTokenException;

	class KnowyTokenException extends KnowySecurityException {

		public KnowyTokenException(String message) {
			super(message);
		}

		public KnowyTokenException(Throwable cause) {
			super(cause);
		}

		public KnowyTokenException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
