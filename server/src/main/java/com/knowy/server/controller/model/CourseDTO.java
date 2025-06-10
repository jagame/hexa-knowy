package com.knowy.server.controller.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CourseDTO {
	public CourseDTO() {}
	private String name;
	private int percentageCompleted;
	private List<LessonDTO> lessons;
}
