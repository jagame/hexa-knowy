package com.knowy.server.controller.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinksLessonDto {
	private String linkTitle;
	private String linkUrl;
	private LinkType type;
	private String fileName;

	public enum LinkType{
		EXTERNAL,
		DOCUMENT
	}
}




