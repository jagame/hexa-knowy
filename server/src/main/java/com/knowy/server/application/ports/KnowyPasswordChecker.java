package com.knowy.server.application.ports;

import com.knowy.server.application.domain.UserPrivate;
import com.knowy.server.application.exception.KnowyPasswordFormatException;
import com.knowy.server.application.exception.KnowyWrongPasswordException;

public interface KnowyPasswordChecker {
	void assertPasswordFormatIsRight(String password) throws KnowyPasswordFormatException;

	boolean isRightPasswordFormat(String password);

	void assertHasPassword(UserPrivate user, String password) throws KnowyWrongPasswordException;

	boolean hasPassword(UserPrivate user, String password);
}
