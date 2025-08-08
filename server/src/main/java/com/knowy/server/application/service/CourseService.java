package com.knowy.server.application.service;

import com.knowy.server.application.domain.*;
import com.knowy.server.application.exception.data.inconsistent.KnowyInconsistentDataException;
import com.knowy.server.application.exception.data.inconsistent.notfound.KnowyUserNotFoundException;
import com.knowy.server.application.ports.CategoryRepository;
import com.knowy.server.application.ports.CourseRepository;
import com.knowy.server.application.ports.LessonRepository;
import com.knowy.server.application.ports.UserLessonRepository;
import com.knowy.server.application.exception.KnowyCourseSubscriptionException;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseService {

	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	private final UserLessonRepository userLessonRepository;
	private final CategoryRepository categoryRepository;

	public CourseService(
		CourseRepository courseRepository,
		LessonRepository lessonRepository,
		UserLessonRepository userLessonRepository,
		CategoryRepository categoryRepository
	) {
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.userLessonRepository = userLessonRepository;
		this.categoryRepository = categoryRepository;
	}


	public List<Course> getUserCourses(Integer userId) throws KnowyInconsistentDataException {
		return findCoursesByUserId(userId);
	}

	public List<Course> findCoursesByUserId(Integer userId) throws KnowyInconsistentDataException {
		List<Integer> courseIds = userLessonRepository.findCourseIdsByUserId(userId);
		if (courseIds.isEmpty()) {
			return List.of();
		}
		return courseRepository.findByIdIn(courseIds);
	}

	public List<Course> findAllRandom() {
		return courseRepository.findAllRandom();
	}

	public List<Course> getRecommendedCourses(Integer userId) throws KnowyInconsistentDataException {
		List<Course> userCourses = findCoursesByUserId(userId);

		List<Integer> userCourseIds = userCourses.stream()
			.map(Course::id)
			.toList();

		Set<String> userLanguages = userCourses.stream()
			.flatMap(course -> findLanguagesForCourse(course).stream())
			.collect(Collectors.toSet());

		List<Course> allCourses = findAllCourses()
			.stream()
			.filter(course -> !userCourseIds.contains(course.id()))
			.toList();

		List<Course> langMatching = allCourses.stream()
			.filter(course -> {
				List<String> courseLanguages = findLanguagesForCourse(course);
				return courseLanguages.stream().anyMatch(userLanguages::contains);
			}).toList();

		List<Course> recommendations = langMatching
			.stream()
			.limit(3)
			.collect(Collectors.toList());

		if (recommendations.size() < 3) {
			List<Course> remaining = allCourses.stream()
				.filter(course -> !langMatching.contains(course))
				.toList();

			for (Course course : remaining) {
				if (recommendations.size() >= 3) {
					break;
				}
				recommendations.add(course);
			}
		}
		return recommendations;
	}

	public void subscribeUserToCourse(User user, int courseId) throws KnowyCourseSubscriptionException,
		KnowyInconsistentDataException, KnowyUserNotFoundException {
		List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
		if (lessons.isEmpty()) {
			throw new KnowyCourseSubscriptionException("El curso no tiene lecciones disponibles");
		}

		ensureAlreadySubscribed(lessons, user.id());

		lessons = lessons.stream()
			.sorted(Comparator.comparing(Lesson::id))
			.toList();

		for (int index = 0; index < lessons.size(); index++) {
			Lesson lesson = lessons.get(index);
			if (!userLessonRepository.existsById(user.id(), lesson.id())) {
				UserLesson userLesson = new UserLesson(
					user,
					lesson,
					LocalDate.now(),
					index == 0 ? UserLesson.ProgressStatus.IN_PROGRESS : UserLesson.ProgressStatus.PENDING
				);
				userLessonRepository.save(userLesson);
			}
		}
	}

	private void ensureAlreadySubscribed(List<Lesson> lessons, Integer userId)
		throws KnowyInconsistentDataException, KnowyCourseSubscriptionException {

		for (Lesson lesson : lessons) {
			if (userLessonRepository.existsByUserIdAndLessonId(userId, lesson.id())) {
				throw new KnowyCourseSubscriptionException("Ya estás suscrito a este curso");
			}
		}
	}

	public List<Course> findAllCourses() {
		return courseRepository.findAll();
	}

	public String findCourseImage(Course course) {
		return course.image() != null ? course.image() : "https://picsum.photos/seed/picsum/200/300";
	}

	public List<String> findLanguagesForCourse(Course course) {
		return course.categories().stream()
			.map(Category::name)
			.toList();
	}

	public int getCourseProgress(Integer userId, Integer courseId) {
		int totalLessons = lessonRepository.countByCourseId(courseId);
		if (totalLessons == 0) return 0;
		int completedLessons;
		try {
			completedLessons = userLessonRepository.countByUserIdAndCourseIdAndStatus(
				userId,
				courseId,
				UserLesson.ProgressStatus.COMPLETED
			);
		} catch (KnowyInconsistentDataException e) {
			return -1;
		}
		return (int) Math.round((completedLessons * 100.0 / totalLessons));
	}

	public List<String> findAllLanguages() {
		return categoryRepository.findAll()
			.stream()
			.map(Category::name)
			.toList();
	}
}
