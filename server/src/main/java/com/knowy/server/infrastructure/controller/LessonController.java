package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.Documentation;
import com.knowy.server.application.domain.UserLesson;
import com.knowy.server.application.exception.KnowyInconsistentDataException;
import com.knowy.server.application.service.UserLessonService;
import com.knowy.server.infrastructure.security.UserSecurityDetails;
import com.knowy.server.infrastructure.controller.dto.CourseDto;
import com.knowy.server.infrastructure.controller.dto.LessonDto;
import com.knowy.server.infrastructure.controller.dto.LinksLessonDto;
import com.knowy.server.infrastructure.controller.dto.SolutionDto;
import com.knowy.server.infrastructure.controller.exception.CurrentLessonNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class LessonController {

	private final UserLessonService userLessonService;

	public LessonController(UserLessonService userLessonService) {
		this.userLessonService = userLessonService;
	}

	/**
	 * Handles the request to display the course introduction page for a specific course.
	 *
	 * <p>This method gathers all the user's lesson progress for the specified course,
	 * builds the corresponding DTOs for course, lessons, and related documentation, and populates the model with the
	 * necessary attributes to render the introduction view.</p>
	 *
	 * @param userDetails The authenticated user details.
	 * @param courseId    The ID of the course being accessed.
	 * @param model       The Spring MVC model to pass data to the view.
	 * @return The name of the Thymeleaf template for the course introduction page.
	 */
	@GetMapping("/{courseId}")
	public String courseIntro(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable("courseId") Integer courseId,
		Model model
	) throws KnowyInconsistentDataException {
		List<UserLesson> userLessons = getAllPublicUserLessons(userDetails.getUser().id(), courseId);
		List<LessonDto> lessonsDto = LessonDto.fromDomains(userLessons);
		List<LinksLessonDto> documentationDto = LinksLessonDto.fromDomains(getAllLessonDocumentations(userLessons));

		CourseDto courseDto = CourseDto.fromDomain(
			userLessons.getFirst().lesson().course(),
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

	private List<Documentation> getAllLessonDocumentations(List<UserLesson> userLessons) {
		return userLessons.stream()
			.map(userLesson -> userLesson.lesson().documentations())
			.flatMap(Collection::stream)
			.toList();
	}

	private Optional<Integer> getNextLessonId(List<LessonDto> lessons) {
		return lessons.stream()
			.filter(lesson -> lesson.status() == LessonDto.LessonStatus.NEXT_LESSON)
			.map(LessonDto::id)
			.findFirst();
	}

	/**
	 * Handles the request to display a specific lesson within a course.
	 *
	 * <p>This method retrieves the current user's progress in the specified course,
	 * identifies the selected lesson, builds the necessary DTOs (lessons, course, documentation, and solutions), and
	 * populates the model to render the lesson explanation view.</p>
	 *
	 * @param userDetails The authenticated user's security details.
	 * @param courseId    The ID, of course.
	 * @param lessonId    The ID of the lesson to display.
	 * @param model       The Spring MVC model used to pass attributes to the view.
	 * @return The name of the Thymeleaf template for the lesson explanation page.
	 */
	@GetMapping("/{courseId}/lesson/{lessonId}")
	public String lesson(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable("courseId") Integer courseId,
		@PathVariable("lessonId") Integer lessonId,
		Model model
	) throws CurrentLessonNotFoundException, KnowyInconsistentDataException {
		List<UserLesson> userLessons = getAllPublicUserLessons(userDetails.getUser().id(), courseId);
		List<LessonDto> lessonsDto = LessonDto.fromDomains(userLessons);

		UserLesson currentUserLesson = getCurrentPublicUserLesson(userLessons, lessonId);
		List<LinksLessonDto> documentationDto = LinksLessonDto.fromDomains(currentUserLesson.lesson().documentations());
		List<SolutionDto> solutionsDto = SolutionDto.fromDomains(currentUserLesson.lesson().exercises());

		CourseDto courseDto = CourseDto.fromDomain(
			userLessons.getFirst().lesson().course(),
			lessonsDto
		);

		populateLesson(model, currentUserLesson, courseDto, lessonsDto, documentationDto, solutionsDto);
		return "pages/lesson-explanation";
	}

	private List<UserLesson> getAllPublicUserLessons(int userId, int courseId) throws KnowyInconsistentDataException {
		return userLessonService
			.findAllByCourseId(userId, courseId);
	}

	private UserLesson getCurrentPublicUserLesson(List<UserLesson> userLessons, int currentLessonId) throws CurrentLessonNotFoundException {
		return userLessons.stream()
			.filter(userLesson -> Objects.equals(userLesson.lesson().id(), currentLessonId))
			.findFirst()
			.orElseThrow(() -> new CurrentLessonNotFoundException("Lección actual no encontrada"));
	}

	private void populateLesson(
		Model model,
		UserLesson currentUserLesson,
		CourseDto courseDto,
		List<LessonDto> lessonsDto,
		List<LinksLessonDto> documentationDto,
		List<SolutionDto> solutions
	) {
		model.addAttribute("course", courseDto);
		model.addAttribute("lesson", LessonDto.fromDomain(currentUserLesson));
		model.addAttribute("lessonContent", currentUserLesson.lesson().explanation());
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
			.orElse(0);
	}
}
