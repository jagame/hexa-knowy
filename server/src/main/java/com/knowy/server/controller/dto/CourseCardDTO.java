package com.knowy.server.controller.dto;

import com.knowy.server.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseCardDTO {
	private Integer id;
	private String name;
	private String creator;
	private int progress;
	private ActionType action;
	private ArrayList<String> languages;

	public enum ActionType {
		START,
		ACQUIRE
	}

	public static CourseCardDTO fromEntity(CourseEntity course, int progress, List<String> languages){
		CourseCardDTO dto = new CourseCardDTO();
		dto.setId(course.getId());
		dto.setName(course.getTitle());
		dto.setCreator(course.getCreator());
		dto.setProgress(progress);
		dto.setAction(ActionType.START);
		dto.setLanguages(new ArrayList<>(languages));
		return dto;
	}

	public static CourseCardDTO fromRecommendation(CourseEntity course, List<String> languages
	) {
		CourseCardDTO dto = new CourseCardDTO();
		dto.setId(course.getId());
		dto.setName(course.getTitle());
		dto.setCreator(course.getCreator());
		dto.setProgress(0);
		dto.setAction(ActionType.ACQUIRE);
		dto.setLanguages(new ArrayList<>(languages));
		return dto;
	}
}
