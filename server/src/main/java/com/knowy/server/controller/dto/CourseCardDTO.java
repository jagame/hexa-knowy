package com.knowy.server.controller.dto;

import com.knowy.server.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCardDTO {
	private Integer id;
	private String name;
	private String author;
	private int progress;
	private ActionType action;
	private ArrayList<String> languages;
	private String imageUrl;
	private LocalDateTime creationDate;

	public enum ActionType {
		START,
		ACQUIRE
	}

	public static CourseCardDTO fromEntity(CourseEntity course, int progress, List<String> languages, LocalDateTime creationDate) {
		CourseCardDTO dto = new CourseCardDTO();
		dto.setId(course.getId());
		dto.setName(course.getTitle());
		dto.setAuthor(course.getAuthor());
		dto.setProgress(progress);
		dto.setAction(ActionType.START);
		dto.setLanguages(new ArrayList<>(languages));
		dto.setCreationDate(creationDate);
		return dto;
	}

	public static CourseCardDTO fromRecommendation(CourseEntity course, List<String> languages, LocalDateTime creationDate
	) {
		CourseCardDTO dto = new CourseCardDTO();
		dto.setId(course.getId());
		dto.setName(course.getTitle());
		dto.setAuthor(course.getAuthor());
		dto.setProgress(0);
		dto.setAction(ActionType.ACQUIRE);
		dto.setLanguages(new ArrayList<>(languages));
		dto.setCreationDate(creationDate);
		return dto;
	}
}
