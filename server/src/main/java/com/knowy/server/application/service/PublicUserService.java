package com.knowy.server.application.service;

import com.knowy.server.application.domain.error.KnowyException;
import com.knowy.server.application.domain.PublicUser;
import com.knowy.server.application.port.persistence.PublicUserRepository;
import com.knowy.server.application.port.persistence.PublicUserRepository.KnowyPublicUserPersistenceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.util.function.Predicate.not;


@Service
public class PublicUserService {
	private final PublicUserRepository publicUserRepository;


	public PublicUserService(PublicUserRepository publicUserRepository) {
		this.publicUserRepository = publicUserRepository;
	}

	public Optional<PublicUser> findPublicUserById(Integer id) throws KnowyException {
		return publicUserRepository.findById(id);
	}

	private boolean isCurrentNickname(String newNickname, PublicUser user) {
		return (user.nickname().equals(newNickname));
	}

	public boolean updateNickname(String newNickname, Integer id) throws KnowyPublicUserPersistenceException {
		Optional<PublicUser> optPublicUser = publicUserRepository.findById(id)
			.filter(not(foundUser ->
				isCurrentNickname(newNickname, foundUser)
			));
		if (optPublicUser.isEmpty()) {
			return false;
		}

		PublicUser user = optPublicUser.get();
		user.setNickname(newNickname);
		publicUserRepository.update(user);
		return true;
	}

}
