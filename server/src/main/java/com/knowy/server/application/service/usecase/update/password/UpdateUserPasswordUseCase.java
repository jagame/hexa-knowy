package com.knowy.server.application.service.usecase.update.password;

import com.knowy.server.application.service.usecase.KnowyUseCase;

public class UpdateUserPasswordUseCase implements KnowyUseCase<UpdateUserPasswordCommand, Boolean> {

	@Override
	public Boolean execute(UpdateUserPasswordCommand param) throws KnowyUserPasswordUpdateException {
		//TODO - Implementar descrifrado de Token y verificar datos ocultos para cambiar los datos vía
		// PrivateUserRepository
		return null;
	}

}
