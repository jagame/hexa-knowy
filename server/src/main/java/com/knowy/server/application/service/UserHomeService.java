package com.knowy.server.application.service;

import com.knowy.server.application.domain.Course;
import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;

import java.util.List;

public class UserHomeService {
	private final CourseService courseService;

	public UserHomeService(CourseService courseService) {
		this.courseService = courseService;
	}

	public long getCoursesCompleted(int userId) throws KnowyInconsistentDataException {
		List<Course> userCourses = courseService.findCoursesByUserId(userId);
		return userCourses
			.stream()
			.filter(course -> courseService.getCourseProgress(userId, course.id()) == 100)
			.count();
	}

	public long getTotalCourses(int userId) throws KnowyInconsistentDataException {
		return courseService
			.findCoursesByUserId(userId)
			.size();
	}

	public long getCoursesPercentage(int userId) throws KnowyInconsistentDataException {
		long totalCourses = getTotalCourses(userId);
		long coursesCompleted = getCoursesCompleted(userId);
		return (totalCourses == 0)
			? 0
			: (int) Math.round((coursesCompleted * 100.0) / totalCourses);
	}
}
