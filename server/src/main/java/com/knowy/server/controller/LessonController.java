package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseDto;
import com.knowy.server.controller.dto.LessonDto;
import com.knowy.server.controller.dto.LinksLessonDto;
import com.knowy.server.controller.dto.SolutionDto;
import com.knowy.server.entity.DocumentationEntity;
import com.knowy.server.entity.PublicUserLessonEntity;
import com.knowy.server.service.CourseSubscriptionService;
import com.knowy.server.service.PublicUserLessonService;
import com.knowy.server.service.model.UserSecurityDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/course")
public class LessonController {

	private final CourseSubscriptionService courseService;
	private final PublicUserLessonService publicUserLessonService;

	public LessonController(CourseSubscriptionService courseService, PublicUserLessonService publicUserLessonService) {
		this.courseService = courseService;
		this.publicUserLessonService = publicUserLessonService;
	}

	// TODO - JavaDoc
	@GetMapping("/{courseId}")
	public String courseIntro(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable Integer courseId, Model model
	) {
		List<PublicUserLessonEntity> publicUserLesson = getAllPublicUserLessons(userDetails.getPublicUser().getId(), courseId);
		List<LessonDto> lessonsDto = LessonDto.fromEntities(publicUserLesson);
		List<LinksLessonDto> documentationDto = LinksLessonDto.fromEntities(getAllLessonDocumentations(publicUserLesson));

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

	private List<DocumentationEntity> getAllLessonDocumentations(List<PublicUserLessonEntity> publicUserLessonEntities) {
		return publicUserLessonEntities.stream()
			.map(lesson -> lesson.getLessonEntity().getDocumentations())
			.flatMap(Collection::stream)
			.toList();
	}

	private int getNextLessonId(List<LessonDto> lessons) {
		return lessons.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.NEXT_LESSON)
			.findFirst()
			.map(LessonDto::id)
			.orElseThrow();
	}

	// TODO - JavaDoc
	@GetMapping("/{courseId}/lesson/{lessonId}")
	public String lesson(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable Integer courseId,
		@PathVariable Integer lessonId,
		Model model
	) {
		List<PublicUserLessonEntity> publicUserLessons = getAllPublicUserLessons(userDetails.getPublicUser().getId(), courseId);
		List<LessonDto> lessonsDto = LessonDto.fromEntities(publicUserLessons);

		PublicUserLessonEntity currentUserLesson = getCurrentPublicUserLesson(publicUserLessons, lessonId);
		List<LinksLessonDto> documentationDto = LinksLessonDto.fromEntities(currentUserLesson.getLessonEntity().getDocumentations());
		List<SolutionDto> solutionsDto = SolutionDto.fromEntities(currentUserLesson.getLessonEntity().getExercises());

		CourseDto courseDto = CourseDto.fromEntity(
			publicUserLessons.getFirst().getLessonEntity().getCourse(),
			lessonsDto
		);

		populateLesson(model, currentUserLesson, courseDto, lessonsDto, documentationDto, solutionsDto);
		return "pages/lesson-explanation";
	}

	private List<PublicUserLessonEntity> getAllPublicUserLessons(int userId, int courseId) {
		return publicUserLessonService
			.findAllByCourseId(userId, courseId);
	}

	private PublicUserLessonEntity getCurrentPublicUserLesson(List<PublicUserLessonEntity> userLessons, int currentLessonId) {
		return userLessons.stream()
			.filter(lesson -> Objects.equals(lesson.getLessonId(), currentLessonId))
			.findFirst()
			.orElseThrow();
	}

	private void populateLesson(
		Model model,
		PublicUserLessonEntity currentUserLesson,
		CourseDto courseDto,
		List<LessonDto> lessonsDto,
		List<LinksLessonDto> documentationDto,
		List<SolutionDto> solutions
	) {
		model.addAttribute("course", courseDto);
		model.addAttribute("lesson", LessonDto.fromEntity(currentUserLesson));
		model.addAttribute("lessonContent", currentUserLesson.getLessonEntity().getExplanation());
		model.addAttribute("lastLesson", getLastCompletedIndex(lessonsDto));
		model.addAttribute("courseId", courseDto.id());
		model.addAttribute("isIntro", false);
		model.addAttribute("LinksList", documentationDto);
		model.addAttribute("solutions", solutions);
	}

	private int getLastCompletedIndex(List<LessonDto> lessons) {
		return lessons.reversed()
			.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.COMPLETE)
			.findFirst()
			.map(LessonDto::id)
			.orElseThrow();
	}
}
