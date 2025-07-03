package com.knowy.server.application.domain;

import java.io.Serializable;
import java.util.Objects;

public class ProfileImage implements Serializable {

	public static final ProfileImage DEFAULT = new ProfileImage(1, "/images/profile/profile_image_test1.webp");

	private Integer id;
	private String url;

	public ProfileImage(Integer id, String url) {
		this.id = id;
		this.url = url;
	}

	public Integer id() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String url() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		ProfileImage that = (ProfileImage) o;
		return Objects.equals(id, that.id) && Objects.equals(url, that.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, url);
	}
}
