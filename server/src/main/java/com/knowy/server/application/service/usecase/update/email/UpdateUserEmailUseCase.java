package com.knowy.server.application.service.usecase.update.email;

import com.knowy.server.application.domain.Email;
import com.knowy.server.application.domain.Password;
import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.application.domain.error.IllegalKnowyPasswordException;
import com.knowy.server.application.port.persistence.KnowyPersistenceException;
import com.knowy.server.application.port.persistence.PrivateUserRepository;
import com.knowy.server.application.service.usecase.KnowyUseCase;

public class UpdateUserEmailUseCase implements KnowyUseCase<UpdateUserEmailCommand, Void> {

	private final PrivateUserRepository privateUserRepository;

	public UpdateUserEmailUseCase(PrivateUserRepository privateUserRepository) {
		this.privateUserRepository = privateUserRepository;
	}

	@Override
	public Void execute(UpdateUserEmailCommand userEmailUpdate) throws KnowyUserEmailUpdateException, IllegalKnowyPasswordException {
		try {
			PrivateUser user = privateUserRepository.getByEmail(userEmailUpdate.currentEmail());

			Email.assertValid(userEmailUpdate.newEmail());
			Email.assertEquals(user.email(), userEmailUpdate.newEmail());
			Password.assertEquals(user.password(), userEmailUpdate.password());

			user.setEmail(new Email(userEmailUpdate.newEmail()));
			privateUserRepository.update(user);
			return null;
		} catch (KnowyPersistenceException e) {
			throw new KnowyUserEmailUpdateException(e);
		}
	}

}
