package com.knowy.server.service;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.controller.exception.KnowyCourseSubscriptionException;
import com.knowy.server.entity.*;
import com.knowy.server.repository.ports.CourseRepository;
import com.knowy.server.repository.ports.LanguageRepository;
import com.knowy.server.repository.ports.LessonRepository;
import com.knowy.server.repository.ports.PublicUserLessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CourseSubscriptionService {
	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	private final PublicUserLessonRepository publicUserLessonRepository;
	private final LanguageRepository languageRepository;

	public CourseSubscriptionService(
		CourseRepository courseRepository,
		LessonRepository lessonRepository,
		PublicUserLessonRepository publicUserLessonRepository,
		LanguageRepository languageRepository
	) {
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.publicUserLessonRepository = publicUserLessonRepository;
		this.languageRepository = languageRepository;
	}

	public List<CourseEntity> findCoursesByUserId(Integer userId) {
		List<Integer> courseIds = publicUserLessonRepository.findCourseIdsByUserId(userId);
		if (courseIds.isEmpty()) return List.of();
		return courseRepository.findByIdIn(courseIds);
	}

	public List<CourseEntity> getRecommendedCourses(Integer userId) {
		List<CourseEntity> userCourses = findCoursesByUserId(userId);

		List<Integer> userCourseIds = userCourses.stream()
			.map(CourseEntity::getId)
			.toList();

		Set<String> userLanguages = userCourses.stream()
			.flatMap(course -> findLanguagesForCourse(course).stream())
			.collect(Collectors.toSet());

		List<CourseEntity> allCourses = findAllCourses().stream()
			.filter(course -> !userCourseIds.contains(course.getId()))
			.toList();

		List<CourseEntity> langMatching = allCourses.stream()
			.filter(course -> {
				List<String> courseLangs = findLanguagesForCourse(course);
				return courseLangs.stream().anyMatch(userLanguages::contains);
			}).toList();

		List<CourseEntity> recommendations = langMatching.stream()
			.limit(3)
			.collect(Collectors.toList());

		if (recommendations.size() < 3) {
			List<CourseEntity> remaining = allCourses.stream()
				.filter(course -> !langMatching.contains(course))
				.toList();

			for(CourseEntity course : remaining){
				if(recommendations.size() >= 3){
					break;
				}
				recommendations.add(course);
			}
		}
		return recommendations;
	}

	public void subscribeUserToCourse(Integer userId, Integer courseId) throws KnowyCourseSubscriptionException{
		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		if (lessons.isEmpty()) {
			throw new KnowyCourseSubscriptionException("El curso no tiene lecciones disponibles");
		}

		boolean alreadySubscribed = lessons.stream()
			.allMatch(lesson ->
				publicUserLessonRepository.existsByUserIdAndLessonId(userId, lesson.getId()));
		if (alreadySubscribed) {
			throw new KnowyCourseSubscriptionException("Ya estás suscrito a este curso");
		}

		lessons = lessons.stream()
		.sorted(Comparator.comparing(LessonEntity::getId))
		.toList();

		for (int i = 0; i < lessons.size(); i++) {
			LessonEntity lesson = lessons.get(i);
			PublicUserLessonIdEntity id = new PublicUserLessonIdEntity(userId, lesson.getId());
			if (!publicUserLessonRepository.existsById(id)) {
				PublicUserLessonEntity pul = new PublicUserLessonEntity();
				pul.setUserId(userId);
				pul.setLessonId(lesson.getId());
				pul.setStartDate(LocalDate.now());
				pul.setStatus(i == 0 ? "in_progress" : "pending");
				publicUserLessonRepository.save(pul);
			}
		}
	}


	public List<CourseEntity> findAllCourses() {
		return courseRepository.findAll();
	}

	public List<String> findLanguagesForCourse(CourseEntity course) {
		return course.getLanguages().stream()
			.map(LanguageEntity::getName)
			.toList();
	}

	public int getCourseProgress(Integer userId, Integer courseId) {
		int totalLessons = lessonRepository.countByCourseId(courseId);
		if (totalLessons == 0) return 0;
		int completedLessons = publicUserLessonRepository.countByUserIdAndCourseIdAndStatus(userId, courseId, "completed");
		return (int) Math.round((completedLessons * 100.0 / totalLessons));
	}
	public List<String> findAllLanguages() {
		return languageRepository.findAll()
			.stream()
			.map(LanguageEntity::getName)
			.toList();
	}
}
