package com.knowy.server.service;

import com.knowy.server.controller.dto.*;
import com.knowy.server.controller.exception.KnowyCourseSubscriptionException;
import com.knowy.server.entity.*;
import com.knowy.server.repository.ports.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	public LessonPageDataDTO getCourseOverviewWithLessons(Integer userId, Integer courseId) {
		CourseEntity course = courseRepository.findById(courseId)
			.orElseThrow(() -> new RuntimeException("Curso no encontrado"));

		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		List<LessonDTO> lessonDTOs = new ArrayList<>();

		int lastCompletedIndex = -1;
		Integer nextLessonId = null;

		for (int i = 0; i < lessons.size(); i++) {
			LessonEntity lesson = lessons.get(i);
			LessonDTO.LessonStatus lessonStatus = determineLessonStatus(userId, lesson.getId());

			if (lessonStatus == LessonDTO.LessonStatus.COMPLETE) {
				lastCompletedIndex = i;
			} else if (lessonStatus == LessonDTO.LessonStatus.NEXT_LESSON && nextLessonId == null) {
				nextLessonId = lesson.getId();
			}

			lessonDTOs.add(new LessonDTO(
				i + 1,
				lesson.getId(),
				lesson.getTitle(),
				null,
				null,
				lessonStatus
			));
		}

		CourseDTO courseDTO = new CourseDTO(
			course.getTitle(),
			getCourseProgress(userId, courseId),
			lessonDTOs,
			course.getDescription(),
			course.getImage(),
			findLanguagesForCourse(course)
		);

		return new LessonPageDataDTO(
			courseDTO,
			null,
			null,
			lastCompletedIndex + 2,
			nextLessonId
		);
	}

	private LessonDTO.LessonStatus determineLessonStatus(Integer userId, Integer lessonId) {
		String status = publicUserLessonRepository.findById(new PublicUserLessonIdEntity(userId, lessonId))
			.map(PublicUserLessonEntity::getStatus)
			.orElse("blocked");

		return switch (status.toLowerCase()) {
			case "completed" -> LessonDTO.LessonStatus.COMPLETE;
			case "in_progress" -> LessonDTO.LessonStatus.NEXT_LESSON;
			default -> LessonDTO.LessonStatus.BLOCKED;
		};
	}

	public LessonPageDataDTO getLessonViewData(Integer userId, Integer courseId, Integer lessonId) {
		LessonPageDataDTO base = getCourseOverviewWithLessons(userId, courseId);

		LessonEntity currentLesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new RuntimeException("Lección no encontrada"));

		int indexInList = findLessonIndex(base.getCourse().getLessons(), currentLesson.getTitle());

		base.setLesson(base.getCourse().getLessons().get(indexInList));
		base.setLessonContent(currentLesson.getExplanation());

		return base;
	}

	private int findLessonIndex(List<LessonDTO> lessons, String title) {
		for (int i = 0; i < lessons.size(); i++) {
			if (lessons.get(i).getTitle().equalsIgnoreCase(title)) return i;
		}
		throw new RuntimeException("No se encontró la lección");
	}

	public List<LinksLessonDto> getLessonDocuments(Integer lessonId) {
		LessonEntity lesson = lessonRepository.findById(lessonId)
			.orElseThrow(() -> new RuntimeException("Lección no encontrada"));

		if (lesson.getDocumentations() == null) return List.of();

		return lesson.getDocumentations().stream()
			.map(this::mapToLinksLessonDto)
			.toList();
	}

	public List<LinksLessonDto> getAllCourseDocuments(Integer courseId) {
		return lessonRepository.findByCourseId(courseId).stream()
			.flatMap(lesson -> Optional.ofNullable(lesson.getDocumentations()).stream().flatMap(Collection::stream))
			.map(this::mapToLinksLessonDto)
			.distinct()
			.toList();
	}
	private LinksLessonDto mapToLinksLessonDto(DocumentationEntity doc) {
		boolean isExternal = doc.getLink().startsWith("http");
		String fileName = (!isExternal && doc.getLink().contains("/"))
			? doc.getLink().substring(doc.getLink().lastIndexOf("/") + 1)
			: null;

		return new LinksLessonDto(
			doc.getTitle(),
			doc.getLink(),
			isExternal ? LinksLessonDto.LinkType.EXTERNAL : LinksLessonDto.LinkType.DOCUMENT,
			fileName
		);
	}

	public List<SolutionDto> getLessonSolutions(Integer lessonId) {
		List<ExerciseEntity> exercises = exerciseRepository.findByLessonId(lessonId);

		return exercises.stream()
			.map(exercise -> {
				OptionEntity correct = exercise.getOptions().stream()
					.filter(OptionEntity::isCorrect)
					.findFirst()
					.orElse(null);
				return correct != null
					? new SolutionDto("Ejercicio " + (exercises.indexOf(exercise) + 1), exercise.getQuestion(), correct.getOptionText())
					: null;
			})
			.filter(Objects::nonNull)
			.toList();
	}
}
