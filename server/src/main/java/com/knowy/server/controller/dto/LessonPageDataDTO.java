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
	private CourseDto course;
	private LessonDto lesson;
	private String lessonContent;
	private int lastLesson;
	private Integer nextLessonId;
}
