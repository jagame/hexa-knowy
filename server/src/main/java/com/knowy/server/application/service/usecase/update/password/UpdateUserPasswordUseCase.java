package com.knowy.server.application.service.usecase.update.password;

import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.domain.error.KnowySecurityException;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.port.security.TokenMapper;
import com.knowy.server.application.service.usecase.KnowyUseCase;

public class UpdateUserPasswordUseCase implements KnowyUseCase<UpdateUserPasswordCommand, PrivateUser> {

	private final PrivateUserRepository privateUserRepository;
	private final TokenMapper tokenMapper;

	public UpdateUserPasswordUseCase(PrivateUserRepository privateUserRepository, TokenMapper tokenMapper) {
		this.privateUserRepository = privateUserRepository;
		this.tokenMapper = tokenMapper;
	}

	@Override
	public PrivateUser execute(UpdateUserPasswordCommand param)
		throws IllegalKnowyPasswordException, KnowyUserPasswordUpdateException, KnowyPersistenceException {

		try {
			PrivateUser privateUser = tokenMapper.translate(param.token());
			if (privateUser.password().hasValue(param.newPassword())) {
				throw new IllegalKnowyPasswordException("New password must not be equals to older one");
			}

			Password.assertValid(param.newPassword());
			privateUser.setPassword(new Password(param.newPassword()));

			privateUserRepository.update(privateUser);

			return privateUser;
		} catch (KnowySecurityException e) {
			throw new KnowyUserPasswordUpdateException("Failed to decode and verify token", e);
		}
	}

}
