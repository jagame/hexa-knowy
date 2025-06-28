package com.knowy.server.application.service.usecase;

import com.knowy.server.application.domain.error.KnowyException;

public interface KnowyUseCase<T, R> {

	R execute(T param) throws KnowyException;

}
