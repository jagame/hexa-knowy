package com.knowy.server.application.service.usecase.login;

import com.knowy.server.application.domain.PublicUser;

import java.util.Objects;

public final class LoginResult {
	private final PublicUser publicUser;
	private final String generatedToken;

	public LoginResult(PublicUser publicUser, String generatedToken) {
		this.publicUser = publicUser;
		this.generatedToken = generatedToken;
	}

	public PublicUser publicUser() {
		return publicUser;
	}

	public String generatedToken() {
		return generatedToken;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (LoginResult) obj;
		return Objects.equals(this.publicUser, that.publicUser) &&
			Objects.equals(this.generatedToken, that.generatedToken);
	}

	@Override
	public int hashCode() {
		return Objects.hash(publicUser, generatedToken);
	}

	@Override
	public String toString() {
		return "LoginResult[" +
			"publicUser=" + publicUser + ", " +
			"generatedToken=" + generatedToken + ']';
	}

}
