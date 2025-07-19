package com.knowy.server.controller;

import com.knowy.server.controller.dto.*;
import com.knowy.server.entity.*;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/course")
public class LessonController {

	private final CourseSubscriptionService courseService;

	public LessonController(CourseSubscriptionService courseService) {
		this.courseService = courseService;
	}

	@GetMapping("/{courseId}")
	public String showCourseIntro(@PathVariable Integer courseId, Model model) {
		Integer userId = getLoggedInUserId();

		CourseEntity course = courseService.getCourseById(courseId);
		List<LessonEntity> lessons = courseService.getLessonsByCourseId(courseId);
		List<LessonDTO> lessonDTOs = new ArrayList<>();

		int lastCompletedIndex = -1;
		Integer nextLessonId = null;

		for (int i = 0; i < lessons.size(); i++) {
			LessonEntity lesson = lessons.get(i);
			String statusStr = courseService.getLessonStatus(userId, lesson.getId());
			LessonDTO.LessonStatus status = switch (statusStr.toLowerCase()) {
				case "completed" -> LessonDTO.LessonStatus.COMPLETE;
				case "in_progress" -> LessonDTO.LessonStatus.NEXT_LESSON;
				default -> LessonDTO.LessonStatus.BLOCKED;
			};

			if (status == LessonDTO.LessonStatus.COMPLETE) {
				lastCompletedIndex = i;
			} else if (status == LessonDTO.LessonStatus.NEXT_LESSON && nextLessonId == null) {
				nextLessonId = lesson.getId();
			}

			lessonDTOs.add(new LessonDTO(
				i + 1,
				lesson.getId(),
				lesson.getTitle(),
				null,
				null,
				status
			));
		}

		CourseDTO courseDTO = new CourseDTO(
			course.getTitle(),
			courseService.getCourseProgress(userId, courseId),
			lessonDTOs,
			course.getDescription(),
			course.getImage(),
			courseService.findLanguagesForCourse(course)
		);

		LessonPageDataDTO data = new LessonPageDataDTO(
			courseDTO,
			null,
			null,
			lastCompletedIndex + 2,
			nextLessonId
		);

		List<DocumentationEntity> rawDocs = courseService.getAllCourseDocumentsRaw(courseId);
		List<LinksLessonDto> docs = mapDocsToLinks(rawDocs);

		model.addAttribute("course", data.getCourse());
		model.addAttribute("lessons", data.getCourse().getLessons());
		model.addAttribute("lastLesson", data.getLastLesson());
		model.addAttribute("nextLessonId", data.getNextLessonId());
		model.addAttribute("courseId", courseId);
		model.addAttribute("isIntro", true);
		model.addAttribute("LinksList", docs);

		return "pages/lesson-explanation";
	}

	@GetMapping("/{courseId}/lesson/{lessonId}")
	public String showLesson(@PathVariable Integer courseId,
							 @PathVariable Integer lessonId,
							 Model model) {
		Integer userId = getLoggedInUserId();

		CourseEntity course = courseService.getCourseById(courseId);
		List<LessonEntity> lessons = courseService.getLessonsByCourseId(courseId);
		List<LessonDTO> lessonDTOs = new ArrayList<>();

		int lastCompletedIndex = -1;
		Integer nextLessonId = null;

		for (int i = 0; i < lessons.size(); i++) {
			LessonEntity lesson = lessons.get(i);
			String statusStr = courseService.getLessonStatus(userId, lesson.getId());
			LessonDTO.LessonStatus status = switch (statusStr.toLowerCase()) {
				case "completed" -> LessonDTO.LessonStatus.COMPLETE;
				case "in_progress" -> LessonDTO.LessonStatus.NEXT_LESSON;
				default -> LessonDTO.LessonStatus.BLOCKED;
			};

			if (status == LessonDTO.LessonStatus.COMPLETE) {
				lastCompletedIndex = i;
			} else if (status == LessonDTO.LessonStatus.NEXT_LESSON && nextLessonId == null) {
				nextLessonId = lesson.getId();
			}

			lessonDTOs.add(new LessonDTO(
				i + 1,
				lesson.getId(),
				lesson.getTitle(),
				null,
				null,
				status
			));
		}

		CourseDTO courseDTO = new CourseDTO(
			course.getTitle(),
			courseService.getCourseProgress(userId, courseId),
			lessonDTOs,
			course.getDescription(),
			course.getImage(),
			courseService.findLanguagesForCourse(course)
		);

		int indexInList = courseService.findLessonIndexByTitle(lessons, courseService.getLessonById(lessonId).getTitle());
		LessonDTO currentLessonDTO = lessonDTOs.get(indexInList);

		LessonPageDataDTO data = new LessonPageDataDTO(
			courseDTO,
			currentLessonDTO,
			courseService.getLessonById(lessonId).getExplanation(),
			lastCompletedIndex + 2,
			nextLessonId
		);

		List<LinksLessonDto> docs = mapDocsToLinks(courseService.getLessonDocumentsRaw(lessonId));
		List<SolutionDto> solutions = courseService.getLessonExercises(lessonId).stream()
			.map(exercise -> {
				Optional<OptionEntity> correct = exercise.getOptions().stream()
					.filter(OptionEntity::isCorrect)
					.findFirst();

				return correct.map(opt -> new SolutionDto(
					"Ejercicio " + (courseService.getLessonExercises(lessonId).indexOf(exercise) + 1),
					exercise.getQuestion(),
					opt.getOptionText()
				)).orElse(null);
			})
			.filter(Objects::nonNull)
			.toList();

		model.addAttribute("course", data.getCourse());
		model.addAttribute("lesson", data.getLesson());
		model.addAttribute("lessonContent", data.getLessonContent());
		model.addAttribute("lastLesson", data.getLastLesson());
		model.addAttribute("courseId", courseId);
		model.addAttribute("isIntro", false);
		model.addAttribute("LinksList", docs);
		model.addAttribute("solutions", solutions);

		return "pages/lesson-explanation";
	}

	private Integer getLoggedInUserId() {
		UserSecurityDetails userDetails = (UserSecurityDetails)
			SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userDetails.getPublicUser().getId();
	}

	private List<LinksLessonDto> mapDocsToLinks(List<DocumentationEntity> docs) {
		return docs.stream().map(doc -> {
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
		}).toList();
	}
}
