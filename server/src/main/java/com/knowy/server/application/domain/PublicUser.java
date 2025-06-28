package com.knowy.server.application.domain;

import java.util.Objects;

public class PublicUser {
	private Integer id;
	private String nickname;

	public PublicUser(Integer id, String nickname) {
		this.id = id;
		this.nickname = nickname;
	}

	public Integer id() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String nickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PublicUser that = (PublicUser) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
}
