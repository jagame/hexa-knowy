package com.knowy.server.infrastructure.controller.dto;

import com.knowy.server.application.domain.Course;
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

	public static CourseBannerDTO fromDomain(Course course) {
		return new CourseBannerDTO(
			course.id(),
			course.title(),
			course.description(),
			course.image(),
			course.author()
		);
	}
}
