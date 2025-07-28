package com.knowy.server.application.domain;

import java.util.Set;

public class User {

	private Integer id;
	private String nickname;
	private ProfileImage profileImage;
	private Set<Category> categories;

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

	public Set<Category> categories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
}
