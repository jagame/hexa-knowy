package com.knowy.server.application.domain;

public class UserPrivate extends User {
	private String email;
	private String password;
	private boolean active = true;

	public UserPrivate() {
	}

	public UserPrivate(User user) {
		this.setId(user.id());
		this.setNickname(user.nickname());
		this.setProfileImage(user.profileImage());
		this.setCategories(user.categories());
	}

	public String email() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String password() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean active() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
}