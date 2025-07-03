package com.knowy.server.application.domain;

import java.io.Serializable;
import java.util.Objects;

public class PublicUser implements Serializable {
	private Integer id;
	private String nickname;
	private ProfileImage profileImage;

	public PublicUser(Integer id, String nickname, ProfileImage profileImage) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
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

	public ProfileImage profileImage() {
		return profileImage;
	}

	public void setProfileImage(ProfileImage profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		PublicUser that = (PublicUser) o;
		return Objects.equals(id, that.id) && Objects.equals(nickname, that.nickname) && Objects.equals(profileImage, that.profileImage);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nickname, profileImage);
	}
}
