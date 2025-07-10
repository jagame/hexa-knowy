package com.knowy.server.util;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Pattern;

@Service
public class PasswordCheker {

	public static void assertPasswordFormatIsRight(String password) throws PasswordFormatException {
		if (!isRightPasswordFormat(password)) {
			throw new PasswordFormatException("Invalid password format");
		}
	}

	public static boolean isRightPasswordFormat(String password) {
		String regex = "^(?=.*\\d)(?=.*[!-/:-@])(?=.*[A-Z])(?=.*[a-z])\\S{8,}$";
		return Pattern.matches(regex, password);
	}

	public static void assertHasPassword(PrivateUserEntity user, String password) throws WrongPasswordException {
		if (!hasPassword(user, password)) {
			throw new WrongPasswordException("Wrong password for user with id: " + user.getId());
		}
	}

	public static boolean hasPassword(PrivateUserEntity user, String password) {
		Objects.requireNonNull(user, "Can't check user password of null user");
		return user.getPassword().equals(password);
	}

}
