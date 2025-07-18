package com.knowy.server.service;

import com.knowy.server.controller.dto.*;
import com.knowy.server.entity.*;
import com.knowy.server.repository.CourseRepository;
import com.knowy.server.repository.LessonRepository;
import com.knowy.server.repository.PublicUserLessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CourseSubscriptionService {
	private final CourseRepository courseRepository;
	private final LessonRepository lessonRepository;
	public final PublicUserLessonRepository publicUserLessonRepository;

	public CourseSubscriptionService(CourseRepository courseRepository, LessonRepository lessonRepository, PublicUserLessonRepository publicUserLessonRepository) {
		this.courseRepository = courseRepository;
		this.lessonRepository = lessonRepository;
		this.publicUserLessonRepository = publicUserLessonRepository;
	}

	public List<CourseCardDTO> getUserCourses(Integer userId) {
		List<CourseEntity> userCourses = findCoursesByUserId(userId);
		return userCourses.stream()
			.map(course -> CourseCardDTO.fromEntity(
				course, getCourseProgress(userId, course.getId()),
				findLanguagesForCourse(course)))
			.toList();
	}

	public List<CourseCardDTO> getRecommendedCourses(Integer userId) {
		List<CourseEntity> allCourses = findAllCourses();
		List<CourseEntity> userCourses = findCoursesByUserId(userId);
		return allCourses.stream()
			.filter(course -> !userCourses.contains(course))
			.map(course -> CourseCardDTO.fromRecommendation(
				course, findLanguagesForCourse(course)))
			.toList();
	}

	public boolean subscribeUserToCourse(Integer userId, Integer courseId) {
		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		if (lessons.isEmpty()) return false;

		boolean alreadySubscribed = lessons.stream()
			.allMatch(lesson ->
				publicUserLessonRepository.existsByUserIdAndLessonId(userId, lesson.getId()));
		if (alreadySubscribed) return true;

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
		return true;
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

	public LessonPageDataDTO getCourseOverviewWithLessons(Integer userId, Integer courseId) {
		CourseEntity course = courseRepository.findById(courseId)
			.orElseThrow(() -> new RuntimeException("Curso no encontrado"));

		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		List<LessonDTO> lessonDTOs = new ArrayList<>();
		int lastCompletedIndex = -1;
		Integer nextLessonId = null;

		for (int i = 0; i < lessons.size(); i++) {
			LessonEntity lesson = lessons.get(i);
			PublicUserLessonIdEntity id = new PublicUserLessonIdEntity(userId, lesson.getId());

			String status = publicUserLessonRepository.findById(id)
				.map(PublicUserLessonEntity::getStatus)
				.orElse("blocked");

			LessonDTO.LessonStatus lessonStatus = switch (status.toLowerCase()) {
				case "completed" -> {
					lastCompletedIndex = i;
					yield LessonDTO.LessonStatus.COMPLETE;
				}
				case "in_progress" -> {
					if (nextLessonId == null) nextLessonId = lesson.getId();
					yield LessonDTO.LessonStatus.NEXT_LESSON;
				}
				default -> LessonDTO.LessonStatus.BLOCKED;
			};

			lessonDTOs.add(new LessonDTO(
				i + 1,
				lesson.getId(),
				lesson.getTitle(),
				null,
				null,
				lessonStatus
			));
		}

		int progress = getCourseProgress(userId, courseId);
		List<String> languages = findLanguagesForCourse(course);

		CourseDTO courseDTO = new CourseDTO(
			course.getTitle(),
			progress,
			lessonDTOs,
			course.getDescription(),
			course.getImage(),
			languages
		);

		return new LessonPageDataDTO(
			courseDTO,
			null,
			null,
			lastCompletedIndex + 2,
			nextLessonId
		);
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
		CourseEntity course = courseRepository.findById(courseId)
			.orElseThrow(() -> new RuntimeException("Curso no encontrado"));

		Set<DocumentationEntity> allDocs = new HashSet<>();
		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		for (LessonEntity lesson : lessons) {
			if (lesson.getDocumentations() != null) {
				allDocs.addAll(lesson.getDocumentations());
			}
		}

		return allDocs.stream()
			.map(this::mapToLinksLessonDto)
			.toList();
	}

	private LinksLessonDto mapToLinksLessonDto(DocumentationEntity doc) {
		LinksLessonDto.LinkType type = doc.getLink().startsWith("http")
			? LinksLessonDto.LinkType.EXTERNAL
			: LinksLessonDto.LinkType.DOCUMENT;

		String fileName = (type == LinksLessonDto.LinkType.DOCUMENT && doc.getLink().contains("/"))
			? doc.getLink().substring(doc.getLink().lastIndexOf("/") + 1)
			: null;

		return new LinksLessonDto(doc.getTitle(), doc.getLink(), type, fileName);
	}
}
