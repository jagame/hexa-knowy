package com.knowy.server.application.ports;

import com.knowy.server.domain.UserPrivate;
import com.knowy.server.application.exception.validation.user.KnowyPasswordFormatException;
import com.knowy.server.application.exception.validation.user.KnowyWrongPasswordException;

public interface KnowyPasswordChecker {
	void assertPasswordFormatIsRight(String password) throws KnowyPasswordFormatException;

	boolean isRightPasswordFormat(String password);

	void assertHasPassword(UserPrivate user, String password) throws KnowyWrongPasswordException;

	boolean hasPassword(UserPrivate user, String password);
}
