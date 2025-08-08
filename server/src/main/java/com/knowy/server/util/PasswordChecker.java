package com.knowy.server.util;

import com.knowy.server.application.domain.UserPrivate;
import com.knowy.server.application.exception.validation.user.KnowyPasswordFormatException;
import com.knowy.server.application.exception.validation.user.KnowyWrongPasswordException;
import com.knowy.server.application.ports.KnowyPasswordChecker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.regex.Pattern;

@Component
public class PasswordChecker implements KnowyPasswordChecker {

	private final PasswordEncoder passwordEncoder;

	public PasswordChecker(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public void assertPasswordFormatIsRight(String password) throws KnowyPasswordFormatException {
		if (!isRightPasswordFormat(password)) {
			throw new KnowyPasswordFormatException("Invalid password format");
		}
	}

	@Override
	public boolean isRightPasswordFormat(String password) {
		String regex = "^(?=.*\\d)(?=.*[!-/:-@])(?=.*[A-Z])(?=.*[a-z])\\S{8,}$";
		return Pattern.matches(regex, password);
	}

	@Override
	public void assertHasPassword(UserPrivate user, String password) throws KnowyWrongPasswordException {
		if (!hasPassword(user, password)) {
			throw new KnowyWrongPasswordException("Wrong password for user with id: " + user.id());
		}
	}

	@Override
	public boolean hasPassword(UserPrivate user, String password) {
		Objects.requireNonNull(user, "Can't check user password of null user");
		return passwordEncoder.matches(password, user.password());
	}
}
