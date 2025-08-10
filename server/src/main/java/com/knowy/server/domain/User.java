package com.knowy.server.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class User implements Serializable {
	private final Integer id;
	private final String nickname;
	private final ProfileImage profileImage;
	private final Set<Category> categories;

	public User(
		Integer id,
		String nickname,
		ProfileImage profileImage,
		Set<Category> categories
	) {
		this.id = id;
		this.nickname = nickname;
		this.profileImage = profileImage;
		this.categories = categories;
	}

	public User(User user) {
		this(user.id(), user.nickname(), user.profileImage(), user.categories());
	}

	public Integer id() {
		return id;
	}

	public String nickname() {
		return nickname;
	}

	public ProfileImage profileImage() {
		return profileImage;
	}

	public Set<Category> categories() {
		return categories;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null || obj.getClass() != this.getClass()) return false;
		var that = (User) obj;
		return Objects.equals(this.id, that.id) &&
			Objects.equals(this.nickname, that.nickname) &&
			Objects.equals(this.profileImage, that.profileImage) &&
			Objects.equals(this.categories, that.categories);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, nickname, profileImage, categories);
	}

	@Override
	public String toString() {
		return "User[" +
			"id=" + id + ", " +
			"nickname=" + nickname + ", " +
			"profileImage=" + profileImage + ", " +
			"categories=" + categories + ']';
	}

}
