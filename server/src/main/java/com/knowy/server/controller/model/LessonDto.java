package com.knowy.server.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LessonDto {
	private String title;
	private String duration;
	private String image;
	private String link;
	private String theory;

	public LessonDto() {
	}

	public LessonDto(String title, String duration, String image, String link, String theory) {
		this.title = title;
		this.duration = duration;
		this.image = image;
		this.link = link;
		this.theory = theory;
	}
}