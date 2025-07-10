package com.knowy.server.util;

import com.knowy.server.entity.PrivateUserEntity;
import com.knowy.server.util.exception.PasswordFormatException;
import com.knowy.server.util.exception.WrongPasswordException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class PasswordChecker {

	private final PasswordEncoder passwordEncoder;

	public PasswordChecker(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public void assertPasswordFormatIsRight(String password) throws PasswordFormatException {
		if (!isRightPasswordFormat(password)) {
			throw new PasswordFormatException("Invalid password format");
		}
	}

	public boolean isRightPasswordFormat(String password) {
		String regex = "^(?=.*\\d)(?=.*[!-/:-@])(?=.*[A-Z])(?=.*[a-z])\\S{8,}$";
		return Pattern.matches(regex, password);
	}

	public void assertHasPassword(PrivateUserEntity user, String password) throws WrongPasswordException {
		if (!hasPassword(user, password)) {
			throw new WrongPasswordException("Wrong password for user with id: " + user.getId());
		}
	}

	public boolean hasPassword(PrivateUserEntity user, String password) {
		Objects.requireNonNull(user, "Can't check user password of null user");
		return passwordEncoder.matches(password, user.getPassword());
	}
}
