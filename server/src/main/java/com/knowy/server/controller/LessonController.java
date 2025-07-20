package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseDto;
import com.knowy.server.controller.dto.LessonDto;
import com.knowy.server.controller.dto.LinksLessonDto;
import com.knowy.server.controller.dto.SolutionDto;
import com.knowy.server.entity.CourseEntity;
import com.knowy.server.entity.LessonEntity;
import com.knowy.server.entity.OptionEntity;
import com.knowy.server.entity.PublicUserLessonEntity;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.PublicUserLessonService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/course")
public class LessonController {

	private final CourseSubscriptionService courseService;
	private final PublicUserLessonService publicUserLessonService;

	public LessonController(CourseSubscriptionService courseService, PublicUserLessonService publicUserLessonService) {
		this.courseService = courseService;
		this.publicUserLessonService = publicUserLessonService;
	}

	@GetMapping("/{courseId}")
	public String showCourseIntro(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable Integer courseId, Model model
	) {
		List<PublicUserLessonEntity> publicUserLesson = publicUserLessonService
			.findAllByCourseId(userDetails.getPublicUser().getId(), courseId);

		List<LessonDto> lessonsDto = publicUserLesson.stream()
			.map(LessonDto::fromEntity)
			.toList();

		List<LinksLessonDto> documentationDto = publicUserLesson.stream()
			.map(l -> l.getLessonEntity().getDocumentations())
			.flatMap(Collection::stream)
			.map(LinksLessonDto::fromEntity)
			.distinct()
			.toList();

		CourseDto courseDto = CourseDto.fromEntity(
			publicUserLesson.getFirst().getLessonEntity().getCourse(),
			lessonsDto
		);

		populateCourseIntro(model, courseDto, lessonsDto, documentationDto);
		return "pages/lesson-explanation";
	}

	private void populateCourseIntro(
		Model model, CourseDto courseDto,
		List<LessonDto> lessonsDto,
		List<LinksLessonDto> documentationDto
	) {
		model.addAttribute("course", courseDto);
		model.addAttribute("lessons", courseDto.lessons());
		model.addAttribute("lastLesson", getLastCompletedIndex(lessonsDto));
		model.addAttribute("nextLessonId", getNextLessonId(lessonsDto));
		model.addAttribute("courseId", courseDto.id());
		model.addAttribute("isIntro", true);
		model.addAttribute("LinksList", documentationDto);
	}

	private int getLastCompletedIndex(List<LessonDto> lessons) {
		return lessons.reversed()
			.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.COMPLETE)
			.findFirst()
			.map(LessonDto::id)
			.orElseThrow();
	}

	private int getNextLessonId(List<LessonDto> lessons) {
		return lessons.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.NEXT_LESSON)
			.findFirst()
			.map(LessonDto::id)
			.orElseThrow();
	}

	@GetMapping("/{courseId}/lesson/{lessonId}")
	public String showLesson(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable Integer courseId,
		@PathVariable Integer lessonId,
		Model model
	) {
		List<PublicUserLessonEntity> publicUserLesson = publicUserLessonService
			.findAllByCourseId(userDetails.getPublicUser().getId(), courseId);

		Integer userId = getLoggedInUserId();

		CourseEntity course = courseService.getCourseById(courseId);
		List<LessonEntity> lessons = courseService.getLessonsByCourseId(courseId);
		List<LessonDto> lessonDtos = new ArrayList<>();

		int lastCompletedIndex = -1;
		Integer nextLessonId = null;

		for (int i = 0; i < lessons.size(); i++) {
			LessonEntity lesson = lessons.get(i);
			String statusStr = courseService.getLessonStatus(userId, lesson.getId());
			LessonDto.LessonStatus status = switch (statusStr.toLowerCase()) {
				case "completed" -> LessonDto.LessonStatus.COMPLETE;
				case "in_progress" -> LessonDto.LessonStatus.NEXT_LESSON;
				default -> LessonDto.LessonStatus.BLOCKED;
			};

			if (status == LessonDto.LessonStatus.COMPLETE) {
				lastCompletedIndex = i;
			} else if (status == LessonDto.LessonStatus.NEXT_LESSON && nextLessonId == null) {
				nextLessonId = lesson.getId();
			}

			lessonDtos.add(new LessonDto(
				lesson.getId(),
				lesson.getTitle(),
				null,
				null,
				status
			));
		}

		CourseDto courseDto = new CourseDto(
			course.getId(),
			course.getTitle(),
			courseService.getCourseProgress(userId, courseId),
			lessonDtos,
			course.getDescription(),
			course.getImage(),
			courseService.findLanguagesForCourse(course)
		);

		int indexInList = courseService.findLessonIndexByTitle(lessons, courseService.getLessonById(lessonId).getTitle());
		LessonDto currentLessonDto = lessonDtos.get(indexInList);

		List<LinksLessonDto> docs = publicUserLesson.stream()
			.map(l -> l.getLessonEntity().getDocumentations())
			.flatMap(Collection::stream)
			.map(LinksLessonDto::fromEntity)
			.distinct()
			.toList();

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

		model.addAttribute("course", courseDto);
		model.addAttribute("lesson", currentLessonDto);
		model.addAttribute("lessonContent", courseService.getLessonById(lessonId).getExplanation());
		model.addAttribute("lastLesson", lastCompletedIndex + 2);
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


}
