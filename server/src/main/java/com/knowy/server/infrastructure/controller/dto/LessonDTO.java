package com.knowy.server.infrastructure.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LessonDTO {
	private int number;
	private String title;
	private String image;
	private int duration;
	private LessonStatus status; //depende de ENUM LessonStatus

	public enum LessonStatus {
		COMPLETE,
		NEXT_LESSON,
		BLOCKED
	}
}


