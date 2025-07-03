package com.knowy.server.application.service.usecase.register;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.persistence.PublicUserRepository;
import com.knowy.server.application.service.exception.InvalidUserException;
import com.knowy.server.application.service.usecase.KnowyUseCase;

public class UserSignUpUseCase implements KnowyUseCase<UserSignUpCommand, PrivateUser> {

	private final PrivateUserRepository privateUserRepository;
	private final PublicUserRepository publicUserRepository;

	public UserSignUpUseCase(PrivateUserRepository privateUserRepository, PublicUserRepository publicUserRepository) {
		this.privateUserRepository = privateUserRepository;
		this.publicUserRepository = publicUserRepository;
	}

	/**
	 * Sign-up a new user with the attributes specified in the {@linkplain UserSignUpCommand}
	 *
	 * @param userSignUpCommand the sign-up command
	 * @return The new registered user
	 * @throws IllegalKnowyPasswordException If the password don't complain with the security requirements
	 * @throws IllegalKnowyEmailException If the value specified as email is not recognized as valid
	 * @throws InvalidUserException If a user with the specified email already exists
	 * @throws KnowyPersistenceException If an error occurs during the verification and storage of user data
	 */
	public PrivateUser execute(UserSignUpCommand userSignUpCommand)
		throws IllegalKnowyPasswordException, IllegalKnowyEmailException, InvalidUserException, KnowyPersistenceException {

		if (publicUserRepository.findByNickname(userSignUpCommand.nickname()).isPresent()) {
			throw new InvalidUserException("El nombre de usuario ya existe.");
		}

		Email.assertValid(userSignUpCommand.email());
		var email = new Email(userSignUpCommand.email());
		if (privateUserRepository.findByEmail(email).isPresent()) {
			throw new InvalidUserException("El email ya está en uso.");
		}

		Password.assertValid(userSignUpCommand.password());
		var password = new Password(userSignUpCommand.password());

		var newUser = new PrivateUser(
			null,
			userSignUpCommand.nickname(),
			userSignUpCommand.profileImage(),
			email,
			password
		);
		privateUserRepository.update(newUser);

		return newUser;
	}

}
