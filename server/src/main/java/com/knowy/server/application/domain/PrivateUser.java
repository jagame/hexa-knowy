package com.knowy.server.application.domain;

import java.util.Objects;

@SuppressWarnings("java:S2160") // Equals check only by id
public class PrivateUser extends PublicUser {
	private Email email;
	private Password password;

	public PrivateUser(Integer id, String nickname, String email, String password) {
		this(id, nickname, new Email(email), new Password(password));
	}

	public PrivateUser(Integer id, String nickname, Email email, Password password) {
		this(new PublicUser(id, nickname), email, password);
	}

	public PrivateUser(PublicUser publicUser, Email email, Password password) {
		super(publicUser.id(), publicUser.nickname());
		this.email = Objects.requireNonNull(email, "An email is required");
		this.password = Objects.requireNonNull(password, "An password is required");
	}

	public Email email() {
		return email;
	}

	public void setEmail(Email email) {
		this.email = email;
	}

	public Password password() {
		return password;
	}

	public void setPassword(Password password) {
		this.password = password;
	}

}
