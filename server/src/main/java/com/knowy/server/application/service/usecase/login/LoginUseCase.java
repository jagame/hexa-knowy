package com.knowy.server.application.service.usecase.login;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.IllegalKnowyEmailException;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.persistence.PrivateUserRepository.KnowyPrivateUserPersistenceException;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.service.usecase.KnowyUseCase;

public class LoginUseCase implements KnowyUseCase<LoginCommand, PrivateUser> {

	private final PrivateUserRepository privateUserRepository;
	private final TokenMapper tokenMapper;

	public LoginUseCase(PrivateUserRepository privateUserRepository, TokenMapper tokenMapper) {
		this.privateUserRepository = privateUserRepository;
		this.tokenMapper = tokenMapper;
	}

	/**
	 * Attempt to execute the specified login command and, if successful, returns the result of the operation,
	 * which includes bot the logged-in user and his session token.
	 *
	 * @param loginCommand The user login command
	 * @return The login result, which includes bot the logged-in user and his session token
	 * @throws KnowyUserNotFoundException    If no user is found with the specified email
	 * @throws IllegalKnowyEmailException    If the specified plainEmail doesn't match with one
	 * @throws IllegalKnowyPasswordException If the specified plainPassword doesn't match with the expected one
	 * @throws KnowyUserLoginException       If an error occurs while finding the user
	 */
	@Override
	public PrivateUser execute(LoginCommand loginCommand)
		throws KnowyUserNotFoundException, IllegalKnowyPasswordException, IllegalKnowyEmailException,
		KnowyUserLoginException {

		try {
			Email.assertValid(loginCommand.email());
			PrivateUser user = privateUserRepository.getByEmail(new Email(loginCommand.email()));
			Password.assertEquals(user.password(), loginCommand.password());
			return user;
		} catch (KnowyPrivateUserPersistenceException e) {
			throw new KnowyUserLoginException(e);
		}
	}

}
