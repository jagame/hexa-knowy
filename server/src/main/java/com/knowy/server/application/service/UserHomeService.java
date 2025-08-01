package com.knowy.server.application.service;

import com.knowy.server.application.domain.Course;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHomeService {
	private final CourseSubscriptionService courseSubscriptionService;

	public UserHomeService(CourseSubscriptionService courseSubscriptionService) {
		this.courseSubscriptionService = courseSubscriptionService;
	}

	public long getCoursesCompleted(int userId) throws KnowyInconsistentDataException {
		List<Course> userCourses = courseSubscriptionService.findCoursesByUserId(userId);
		return userCourses
			.stream()
			.filter(course -> courseSubscriptionService.getCourseProgress(userId, course.id()) == 100)
			.count();
	}

	public long getTotalCourses(int userId) throws KnowyInconsistentDataException {
		return courseSubscriptionService
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
