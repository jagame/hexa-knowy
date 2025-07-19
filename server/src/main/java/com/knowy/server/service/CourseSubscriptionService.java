package com.knowy.server.service;

import com.knowy.server.controller.dto.*;
import com.knowy.server.controller.exception.KnowyCourseSubscriptionException;
import com.knowy.server.entity.*;
import com.knowy.server.repository.ports.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseSubscriptionService {
	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	private final PublicUserLessonRepository publicUserLessonRepository;
	private final LanguageRepository languageRepository;
	private final ExerciseRepository exerciseRepository;

	public CourseSubscriptionService(CourseRepository courseRepository, LessonRepository lessonRepository, PublicUserLessonRepository publicUserLessonRepository, LanguageRepository languageRepository, ExerciseRepository exerciseRepository) {
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.publicUserLessonRepository = publicUserLessonRepository;
		this.languageRepository = languageRepository;
		this.exerciseRepository = exerciseRepository;
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
		List<CourseEntity> userCourses = findCoursesByUserId(userId);
		Set<String> userLanguages = userCourses.stream()
			.flatMap(course -> course.getLanguages().stream())
			.map(LanguageEntity::getName)
			.collect(Collectors.toSet());

		Set<Integer> userCourseIds = userCourses.stream()
			.map(CourseEntity::getId)
			.collect(Collectors.toSet());

		List<CourseEntity> recommendations = findAllCourses().stream()
			.filter(course -> !userCourseIds.contains(course.getId()))
			.sorted((a, b) -> {
				// Ordenar primero los cursos que comparten idioma
				boolean aMatches = matchesAnyLanguage(a, userLanguages);
				boolean bMatches = matchesAnyLanguage(b, userLanguages);
				return Boolean.compare(!aMatches, !bMatches); // true va después
			})
			.limit(3)
			.toList();

		return recommendations.stream()
			.map(course -> CourseCardDTO.fromRecommendation(
				course,
				findLanguagesForCourse(course),
				course.getCreationDate()))
			.toList();
	}

	private boolean matchesAnyLanguage(CourseEntity course, Set<String> userLanguages) {
		return course.getLanguages().stream()
			.map(LanguageEntity::getName)
			.anyMatch(userLanguages::contains);
	}

	public void subscribeUserToCourse(Integer userId, Integer courseId) throws KnowyCourseSubscriptionException {
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

	public CourseEntity getCourseById(Integer courseId) {
		return courseRepository.findById(courseId)
			.orElseThrow(() -> new RuntimeException("Curso no encontrado"));
	}

	public List<LessonEntity> getLessonsByCourseId(Integer courseId) {
		return lessonRepository.findByCourseId(courseId);
	}

	public String getLessonStatus(Integer userId, Integer lessonId) {
		return publicUserLessonRepository.findById(new PublicUserLessonIdEntity(userId, lessonId))
			.map(PublicUserLessonEntity::getStatus)
			.orElse("blocked");
	}

	public LessonEntity getLessonById(Integer lessonId) {
		return lessonRepository.findById(lessonId)
			.orElseThrow(() -> new RuntimeException("Lección no encontrada"));
	}

	public int findLessonIndexByTitle(List<LessonEntity> lessons, String title) {
		for (int i = 0; i < lessons.size(); i++) {
			if (lessons.get(i).getTitle().equalsIgnoreCase(title)) return i;
		}
		throw new RuntimeException("No se encontró la lección");
	}

	public List<DocumentationEntity> getLessonDocumentsRaw(Integer lessonId) {
		LessonEntity lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new RuntimeException("Lección no encontrada"));
		return Optional.ofNullable(lesson.getDocumentations()).orElse(List.of());
	}

	public List<DocumentationEntity> getAllCourseDocumentsRaw(Integer courseId) {
		return lessonRepository.findByCourseId(courseId).stream()
			.flatMap(lesson -> Optional.ofNullable(lesson.getDocumentations()).stream().flatMap(Collection::stream))
			.distinct()
			.toList();
	}

	public List<ExerciseEntity> getLessonExercises(Integer lessonId) {
		return exerciseRepository.findByLessonId(lessonId);
	}
}

