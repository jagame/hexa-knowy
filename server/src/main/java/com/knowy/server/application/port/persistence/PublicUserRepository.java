package com.knowy.server.application.port.persistence;

import com.knowy.server.application.domain.PublicUser;

import java.util.Optional;


public interface PublicUserRepository {

	Optional<PublicUser> findById(Integer id) throws KnowyPublicUserPersistenceException;

	default PublicUser getById(Integer id) throws KnowyPublicUserPersistenceException, KnowyUserNotFoundException {
		return findById(id).orElseThrow(() ->
			new KnowyUserNotFoundException(String.format("No user with id %d was found", id))
		);
	}

	void update(PublicUser publicUser) throws KnowyPublicUserPersistenceException;

	class KnowyPublicUserPersistenceException extends KnowyPersistenceException {

		public KnowyPublicUserPersistenceException(String message, Throwable cause) {
			super(message, cause);
		}

		public KnowyPublicUserPersistenceException(Throwable cause) {
			super(cause);
		}
	}

}
