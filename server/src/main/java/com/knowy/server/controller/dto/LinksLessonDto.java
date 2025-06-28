package com.knowy.server.controller.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LinksLessonDto {
	private String linkTitle;
	private String linkUrl;
	private LinkType type;
	private String fileName;
}




