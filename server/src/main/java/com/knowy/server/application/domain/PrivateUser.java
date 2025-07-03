package com.knowy.server.application.domain;

import java.util.Objects;

@SuppressWarnings("java:S2160") // Equals check only by id
public class PrivateUser extends PublicUser {
	private Email email;
	private Password password;

	public PrivateUser(Integer id, String nickname, ProfileImage profileImage, String email, String password) {
		this(id, nickname, profileImage, new Email(email), new Password(password));
	}

	public PrivateUser(Integer id, String nickname, ProfileImage profileImage, Email email, Password password) {
		this(new PublicUser(id, nickname, profileImage), email, password);
	}

	public PrivateUser(PublicUser publicUser, Email email, Password password) {
		super(publicUser.id(), publicUser.nickname(), publicUser.profileImage());
		this.email = Objects.requireNonNull(email, "An email is required");
		this.password = Objects.requireNonNull(password, "An plainPassword is required");
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

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		PrivateUser user = (PrivateUser) o;
		return Objects.equals(email, user.email) && Objects.equals(password, user.password);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), email, password);
	}
}
