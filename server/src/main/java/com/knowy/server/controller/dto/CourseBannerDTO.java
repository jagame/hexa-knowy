package com.knowy.server.controller.dto;

import com.knowy.server.entity.CourseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseBannerDTO {
	private Integer courseId;
	private String title;
	private String description;
	private String imageUrl;
	private String author;

	public static CourseBannerDTO fromEntity(CourseEntity course) {
		return new CourseBannerDTO(
			course.getId(),
			course.getTitle(),
			course.getDescription(),
			course.getImage(),
			course.getAuthor()
		);
	}
}
