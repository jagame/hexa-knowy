package com.knowy.server.service;

import com.knowy.server.controller.dto.CourseCardDTO;
import com.knowy.server.controller.dto.CourseDTO;
import com.knowy.server.controller.dto.LessonDTO;
import com.knowy.server.controller.dto.LessonPageDataDTO;
import com.knowy.server.entity.*;
import com.knowy.server.repository.CourseRepository;
import com.knowy.server.repository.LessonRepository;
import com.knowy.server.repository.PublicUserLessonRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

	// TODO:Terminar de implementar todo sobre el curso y las lecciones
	public LessonPageDataDTO getCourseOverviewWithLessons(Integer userId, Integer courseId) {
		CourseEntity course = courseRepository.findById(courseId)
			.orElseThrow(() -> new RuntimeException("Curso no encontrado"));

		List<LessonEntity> lessons = lessonRepository.findByCourseId(courseId);
		List<LessonDTO> lessonDTOs = new ArrayList<>();
		int lastCompletedIndex = -1;

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
				case "in_progress" -> LessonDTO.LessonStatus.NEXT_LESSON;
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

		CourseDTO courseDTO = new CourseDTO(course.getTitle(), progress, lessonDTOs, course.getDescription(), course.getImage());

		return new LessonPageDataDTO(
			courseDTO,
			null,
			null,
			lastCompletedIndex + 2
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
}
