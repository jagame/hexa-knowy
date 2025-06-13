package com.knowy.server.controller.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class LessonDTO {
	private int number;
	private String name;
	private LessonStatus status; //depende de ENUM EstadoLeccion

	public enum LessonStatus {
		COMPLETE,
		NEXT_LESSON,
		BLOCKED
	}
}


