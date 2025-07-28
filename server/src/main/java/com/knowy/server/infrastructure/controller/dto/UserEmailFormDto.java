package com.knowy.server.infrastructure.controller.dto;

import java.util.Objects;

public final class UserEmailFormDto {

	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (UserEmailFormDto) obj;
		return Objects.equals(this.email, that.email);
	}

	@Override
	public int hashCode() {
		return Objects.hash(email);
	}

	@Override
	public String toString() {
		return "UserEmailForm[" +
			   "email=" + email + ']';
	}

}
