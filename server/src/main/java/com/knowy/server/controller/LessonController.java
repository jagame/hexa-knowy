package com.knowy.server.controller;

import com.knowy.server.controller.dto.CourseDto;
import com.knowy.server.controller.dto.LessonDto;
import com.knowy.server.controller.dto.LinksLessonDto;
import com.knowy.server.controller.dto.SolutionDto;
import com.knowy.server.controller.exception.CurrentLessonNotFoundException;
import com.knowy.server.entity.DocumentationEntity;
import com.knowy.server.entity.PublicUserLessonEntity;
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
import java.util.Optional;

@Controller
@RequestMapping("/course")
public class LessonController {

	private final PublicUserLessonService publicUserLessonService;

	public LessonController(PublicUserLessonService publicUserLessonService) {
		this.publicUserLessonService = publicUserLessonService;
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
		@PathVariable Integer courseId,
		@PathVariable Integer lessonId,
		Model model
	) throws CurrentLessonNotFoundException {
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

	private PublicUserLessonEntity getCurrentPublicUserLesson(List<PublicUserLessonEntity> userLessons, int currentLessonId) throws CurrentLessonNotFoundException {
		return userLessons.stream()
			.filter(lesson -> Objects.equals(lesson.getLessonId(), currentLessonId))
			.findFirst()
			.orElseThrow(() -> new CurrentLessonNotFoundException("Lección actual no encontrada"));
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
			.orElse(0);
	}
}
