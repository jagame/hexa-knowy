package com.knowy.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LessonPageDataDTO {
	private CourseDTO course;
	private LessonDTO lesson;
	private String lessonContent;
	private int lastLesson;
//	private String level; // texto fijo: "Intermedio"
}
