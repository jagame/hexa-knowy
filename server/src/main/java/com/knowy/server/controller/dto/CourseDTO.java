package com.knowy.server.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
	private String name;
	private int percentageCompleted;
	private List<LessonDTO> lessons;
	private String description;
	private String image;
}
