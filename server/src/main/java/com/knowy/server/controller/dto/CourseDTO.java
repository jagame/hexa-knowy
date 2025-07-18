package com.knowy.server.controller.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CourseDTO {
	private String name;
	private int percentageCompleted;
	private List<LessonDTO> lessons;
}
