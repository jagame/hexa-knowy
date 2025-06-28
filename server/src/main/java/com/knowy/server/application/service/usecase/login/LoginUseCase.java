package com.knowy.server.application.service.usecase.login;

import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.port.persistence.KnowyUserNotFoundException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.persistence.PrivateUserRepository.KnowyPrivateUserPersistenceException;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.service.usecase.KnowyUseCase;

public class LoginUseCase implements KnowyUseCase<LoginCommand, LoginResult> {

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
	 * @throws IllegalKnowyPasswordException If the specified password doesn't match with the expected one
	 * @throws KnowyUserLoginException       If an error occurs while finding the user
	 */
	@Override
	public LoginResult execute(LoginCommand loginCommand)
		throws KnowyUserNotFoundException, IllegalKnowyPasswordException, KnowyUserLoginException {

		try {
			PrivateUser user = privateUserRepository.getByEmail(loginCommand.email());
			Password.assertEquals(user.password(), loginCommand.password());
			return new LoginResult(user, tokenMapper.generate(user));
		} catch (KnowyPrivateUserPersistenceException e) {
			throw new KnowyUserLoginException(e);
		}
	}

}
