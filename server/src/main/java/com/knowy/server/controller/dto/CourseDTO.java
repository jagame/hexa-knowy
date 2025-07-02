package com.knowy.server.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class CourseDTO {
	public CourseDTO() {}
	private String name;
	private int percentageCompleted;
	private List<LessonDTO> lessons;
}
