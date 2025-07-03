package com.knowy.server.util;

import com.knowy.server.application.domain.PrivateUser;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;

import java.util.Objects;
import java.util.regex.Pattern;

public class PasswordCheker {

	public static void assertPasswordFormatIsRight(String password) throws PasswordFormatException {
		if (!isRightPasswordFormat(password)) {
			throw new PasswordFormatException("Invalid plainPassword format");
		}
	}

	public static boolean isRightPasswordFormat(String password) {
		String regex = "^(?=.*\\d)(?=.*[!-/:-@])(?=.*[A-Z])(?=.*[a-z])\\S{8,}$";
		return Pattern.matches(regex, password);
	}

	public static void assertHasPassword(PrivateUser user, String password) throws WrongPasswordException {
		if (!hasPassword(user, password)) {
			throw new WrongPasswordException("Wrong plainPassword for user with id: " + user.id());
		}
	}

	public static boolean hasPassword(PrivateUser user, String password) {
		Objects.requireNonNull(user, "Can't check user plainPassword of null user");
		return user.password().hasValue(password);
	}

}
