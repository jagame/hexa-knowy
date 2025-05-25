package com.knowy.server.controller.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NewsHomeDto {

	private int id;
	private String title;
	private String description;
	private String image;
	private String url;

	public NewsHomeDto(int id, String title, String description, String image, String url) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.image = image;
		this.url = url;
	}
}
