package com.knowy.server.controller;

import com.knowy.server.controller.dto.ExerciseDto;
import com.knowy.server.controller.dto.ExerciseOptionDto;
import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.service.PublicUserExerciseService;
import com.knowy.server.service.PublicUserLessonService;
import com.knowy.server.service.exception.PublicUserLessonException;
import com.knowy.server.service.exception.UserNotFoundException;
import com.knowy.server.service.model.ExerciseDifficult;
import com.knowy.server.service.model.UserSecurityDetails;
import com.knowy.server.util.exception.ExerciseNotFoundException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExerciseController {

	private static final String EXERCISE_MODEL_ATTRIBUTE = "exercise";
	private static final String EXERCISE_HTML_URL = "pages/exercise";

	private final PublicUserExerciseService publicUserExerciseService;
	private final PublicUserLessonService publicUserLessonService;

	/**
	 * The constructor
	 *
	 * @param publicUserExerciseService the publicUserExerciseService
	 */
	public ExerciseController(PublicUserExerciseService publicUserExerciseService, PublicUserLessonService publicUserLessonService) {
		this.publicUserExerciseService = publicUserExerciseService;
		this.publicUserLessonService = publicUserLessonService;
	}

	/**
	 * Handles GET requests to display the next exercise for review within a lesson.
	 *
	 * @param lessonId    the ID of the lesson to get the exercise from
	 * @param userDetails the authenticated user details
	 * @param model       the model to pass data to the view
	 * @return the name of the exercise view page, or error page if exercise not found
	 */
	@GetMapping("/course/{lessonId}/exercise/review")
	public String exerciseLesson(
		@PathVariable("lessonId") int lessonId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		Model model
	) {
		try {
			PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
				.getNextExerciseByLessonId(userDetails.getPublicUser().getId(), lessonId);

			model.addAttribute(EXERCISE_MODEL_ATTRIBUTE, ExerciseDto.fromPublicUserExerciseEntity(publicUserExercise));
			model.addAttribute("mode", "ANSWERING");
			model.addAttribute("formReviewUrl", "/course/exercise/review");
			return EXERCISE_HTML_URL;
		} catch (ExerciseNotFoundException e) {
			return "error/error";
		}
	}

	/**
	 * Handles POST requests when a user submits an answer for an exercise review.
	 *
	 * @param userDetails the authenticated user details
	 * @param exerciseId  the ID of the exercise being answered
	 * @param answerId    the ID of the selected answer option
	 * @param model       the model to pass data to the view
	 * @return the name of the exercise view page showing if the answer was correct or not
	 * @throws ExerciseNotFoundException if the exercise is not found
	 * @throws UserNotFoundException     if the user is not found
	 */
	@PostMapping("/course/exercise/review")
	public String exerciseLessonReview(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("answerId") int answerId,
		Model model
	) throws ExerciseNotFoundException, UserNotFoundException {
		ExerciseDto exerciseDto = ExerciseDto.fromPublicUserExerciseEntity(
			publicUserExerciseService.getByIdOrCreate(userDetails.getPublicUser().getId(), exerciseId),
			answerId
		);

		if (!isCorrectAnswer(exerciseDto.options(), answerId)) {
			model.addAttribute("mode", "FAILING");
		} else {
			model.addAttribute("mode", "REVIEWING");
		}
		model.addAttribute(EXERCISE_MODEL_ATTRIBUTE, exerciseDto);
		model.addAttribute("formEvaluateUrl", "/course/exercise/evaluate");
		return EXERCISE_HTML_URL;
	}

	private boolean isCorrectAnswer(List<ExerciseOptionDto> options, int answer) {
		return options.stream()
			.filter(option -> option.id() == answer)
			.anyMatch(option -> option.status() == ExerciseOptionDto.AnswerStatus.RESPONSE_SUCCESS);
	}

	/**
	 * Handles POST requests to evaluate the user's difficulty rating for an exercise.
	 *
	 * @param userDetails the authenticated user details
	 * @param exerciseId  the ID of the exercise being evaluated
	 * @param evaluation  the difficulty rating provided by the user
	 * @return a redirect to the lesson page if average rate >= 80, otherwise to the exercise review page
	 * @throws ExerciseNotFoundException if the exercise is not found
	 * @throws UserNotFoundException     if the user is not found
	 * @throws PublicUserLessonException if the publicUserLesson is not found
	 */
	@PostMapping("/course/exercise/evaluate")
	public String exerciseLessonEvaluate(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("evaluation") ExerciseDifficult evaluation
	) throws ExerciseNotFoundException, UserNotFoundException, PublicUserLessonException {
		PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
			.getByIdOrCreate(userDetails.getPublicUser().getId(), exerciseId);

		publicUserExerciseService.processUserAnswer(evaluation, publicUserExercise);

		int lessonId = publicUserExercise.getExerciseEntity().getLesson().getId();
		int courseId = publicUserExercise.getExerciseEntity().getLesson().getCourse().getId();

		double average = publicUserExerciseService.getAverageRateByLessonId(lessonId);
		if (average >= 80) {
			publicUserLessonService.updateLessonStatusToCompleted(userDetails.getPublicUser().getId(), lessonId);
			return "redirect:/course/%d".formatted(courseId);
		}
		return "redirect:/course/%d/exercise/review".formatted(lessonId);
	}

	/**
	 * Handles GET requests to show the next exercise for the authenticated user.
	 *
	 * @param userDetails the authenticated user details
	 * @param model       the model to add attributes for the view
	 * @return the exercise page view, or error page if no exercise is found
	 */
	@GetMapping("/exercise/review")
	public String exerciseLesson(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		Model model
	) {
		try {
			PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
				.getNextExerciseByUserId(userDetails.getPublicUser().getId());

			model.addAttribute(EXERCISE_MODEL_ATTRIBUTE, ExerciseDto.fromPublicUserExerciseEntity(publicUserExercise));
			model.addAttribute("mode", "ANSWERING");
			model.addAttribute("formReviewUrl", "/exercise/review");
			return EXERCISE_HTML_URL;
		} catch (ExerciseNotFoundException e) {
			return "error/error";
		}
	}

	/**
	 * Handles POST requests when a user submits an answer to an exercise.
	 *
	 * @param userDetails the authenticated user details
	 * @param exerciseId  the ID of the exercise being answered
	 * @param answerId    the ID of the selected answer option
	 * @param model       the model to add attributes for the view
	 * @return the exercise page view with feedback on the answer
	 * @throws ExerciseNotFoundException if the exercise cannot be found
	 * @throws UserNotFoundException     if the user cannot be found
	 */
	@PostMapping("/exercise/review")
	public String exerciseReview(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("answerId") int answerId,
		Model model
	) throws ExerciseNotFoundException, UserNotFoundException {
		ExerciseDto exerciseDto = ExerciseDto.fromPublicUserExerciseEntity(
			publicUserExerciseService.getByIdOrCreate(userDetails.getPublicUser().getId(), exerciseId),
			answerId
		);

		if (!isCorrectAnswer(exerciseDto.options(), answerId)) {
			model.addAttribute("mode", "FAILING");
		} else {
			model.addAttribute("mode", "REVIEWING");
		}
		model.addAttribute(EXERCISE_MODEL_ATTRIBUTE, exerciseDto);
		model.addAttribute("formEvaluateUrl", "/exercise/evaluate");
		return EXERCISE_HTML_URL;
	}


	/**
	 * Handles POST requests to evaluate the user's difficulty rating for an exercise.
	 *
	 * @param userDetails the authenticated user details
	 * @param exerciseId  the ID of the exercise being evaluated
	 * @param evaluation  the difficulty rating provided by the user
	 * @return a redirect to the exercise review page
	 * @throws ExerciseNotFoundException if the exercise cannot be found
	 * @throws UserNotFoundException     if the user cannot be found
	 */
	@PostMapping("/exercise/evaluate")
	public String exerciseEvaluate(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("evaluation") ExerciseDifficult evaluation
	) throws ExerciseNotFoundException, UserNotFoundException {
		PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
			.getByIdOrCreate(userDetails.getPublicUser().getId(), exerciseId);

		publicUserExerciseService.processUserAnswer(evaluation, publicUserExercise);
		return "redirect:/exercise/review";
	}
}
