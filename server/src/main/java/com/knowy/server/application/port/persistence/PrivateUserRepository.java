package com.knowy.server.application.port.persistence;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.PrivateUser;

import java.util.Optional;


public interface PrivateUserRepository {

	Optional<PrivateUser> findById(int id) throws KnowyPrivateUserPersistenceException;

	Optional<PrivateUser> findByEmail(Email email) throws KnowyPrivateUserPersistenceException;

	/**
	 * Find the private data of the user whose email matches the specified one.
	 *
	 * @param email The email of the wanted user
	 * @return The private user whose email matches the specified one
	 * @throws KnowyUserNotFoundException    If no user with specified email is found
	 * @throws KnowyPrivateUserPersistenceException If an error occurs while finding the user
	 */
	default PrivateUser getByEmail(Email email) throws KnowyPrivateUserPersistenceException, KnowyUserNotFoundException {
		return findByEmail(email)
			.orElseThrow(() -> new KnowyUserNotFoundException(
				"No user with email <%s> was found".formatted(email)
			));
	}

	Optional<PrivateUser> findByToken(String token) throws KnowyPrivateUserPersistenceException;

	void update(PrivateUser privateUser) throws KnowyPrivateUserPersistenceException;

	class KnowyPrivateUserPersistenceException extends KnowyPersistenceException {

		public KnowyPrivateUserPersistenceException(String message) {
			super(message);
		}

		public KnowyPrivateUserPersistenceException(String message, Throwable cause) {
			super(message, cause);
		}

		public KnowyPrivateUserPersistenceException(Throwable cause) {
			super(cause);
		}
	}

}
