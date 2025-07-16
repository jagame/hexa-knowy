package com.knowy.server.service;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.controller.exception.KnowyCourseSubscriptionException;
import com.knowy.server.entity.*;
import com.knowy.server.repository.CourseRepository;
import com.knowy.server.repository.LanguageRepository;
import com.knowy.server.repository.LessonRepository;
import com.knowy.server.repository.PublicUserLessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CourseSubscriptionService {
	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	private final PublicUserLessonRepository publicUserLessonRepository;
	private final LanguageRepository languageRepository;

	public CourseSubscriptionService(CourseRepository courseRepository, LessonRepository lessonRepository, PublicUserLessonRepository publicUserLessonRepository, LanguageRepository languageRepository) {
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.publicUserLessonRepository = publicUserLessonRepository;
		this.languageRepository = languageRepository;
	}

	public List<CourseCardDTO> getUserCourses(Integer userId) {
		List<CourseEntity> userCourses = findCoursesByUserId(userId);
		return userCourses.stream()
			.map(course -> CourseCardDTO.fromEntity(
				course, getCourseProgress(userId, course.getId()),
				findLanguagesForCourse(course), course.getCreationDate()))
			.toList();
	}

	public List<CourseCardDTO> getRecommendedCourses(Integer userId) {
		List<CourseEntity> allCourses = findAllCourses();
		List<CourseEntity> userCourses = findCoursesByUserId(userId);
		return allCourses.stream()
			.filter(course -> !userCourses.contains(course))
			.map(course -> CourseCardDTO.fromRecommendation(
				course, findLanguagesForCourse(course), course.getCreationDate()))
			.toList();
	}

	public void subscribeUserToCourse(Integer userId, Integer courseId) {
		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		if (lessons.isEmpty()){
			throw new KnowyCourseSubscriptionException("El curso no tiene lecciones disponibles");
		}

		boolean alreadySubscribed = lessons.stream()
			.allMatch(lesson ->
				publicUserLessonRepository.existsByUserIdAndLessonId(userId, lesson.getId()));
		if (alreadySubscribed){
			throw new KnowyCourseSubscriptionException("Ya estás suscrito a este curso");
		}

		for (LessonEntity lesson : lessons) {
			PublicUserLessonIdEntity id = new PublicUserLessonIdEntity(userId, lesson.getId());
			if (!publicUserLessonRepository.existsById(id)) {
				PublicUserLessonEntity pul = new PublicUserLessonEntity();
				pul.setUserId(userId);
				pul.setLessonId(lesson.getId());
				pul.setStartDate(LocalDate.now());
				pul.setStatus("pending");
				publicUserLessonRepository.save(pul);
			}
		}
	}

	public List<CourseEntity> findCoursesByUserId(Integer userId) {
		List<Integer> courseIds = publicUserLessonRepository.findCourseIdsByUserId(userId);
		if (courseIds.isEmpty()) return List.of();
		return courseRepository.findByIdIn(courseIds);
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
