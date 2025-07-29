package com.knowy.server.application.domain;

import java.util.Set;

public class UserPrivate extends User {
	private final String email;
	private final String password;
	private final boolean active;

	public UserPrivate(
		Integer id,
		String nickname,
		ProfileImage profileImage,
		Set<Category> categories,
		String email,
		String password,
		boolean active
	) {
		super(id, nickname, profileImage, categories);
		this.email = email;
		this.password = password;
		this.active = active;
	}

	public UserPrivate(User user, String email, String password, boolean active) {
		this(user.id(), user.nickname(), user.profileImage(), user.categories(), email, password, active);
	}

	public UserPrivate(User user, String email, String password) {
		this(user, email, password, true);
	}

	public String email() {
		return email;
	}

	public String password() {
		return password;
	}

	public boolean active() {
		return active;
	}

	public User cropToUser() {
		return new User(this.id(), this.nickname(), this.profileImage(), this.categories());
	}
}