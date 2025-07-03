package com.knowy.server.application.port.security;

import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.KnowySecurityException;

import java.util.Optional;

public interface TokenMapper {

	String generate(PrivateUser user) throws KnowyTokenGenerationException;

	boolean isValid(String token);

	PrivateUser translate(String token) throws KnowyTokenTranslationException;

	Optional<PrivateUser> translateUnverified(String token) throws KnowyTokenTranslationException;

	class KnowyTokenGenerationException extends KnowySecurityException {

		public KnowyTokenGenerationException(String message) {
			super(message);
		}

		public KnowyTokenGenerationException(Throwable cause) {
			super(cause);
		}

		public KnowyTokenGenerationException(String message, Throwable cause) {
			super(message, cause);
		}
	}

	class KnowyTokenTranslationException extends KnowySecurityException {

		public KnowyTokenTranslationException(String message) {
			super(message);
		}

		public KnowyTokenTranslationException(Throwable cause) {
			super(cause);
		}

		public KnowyTokenTranslationException(String message, Throwable cause) {
			super(message, cause);
		}
	}
}
