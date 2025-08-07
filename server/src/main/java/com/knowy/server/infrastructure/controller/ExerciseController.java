package com.knowy.server.infrastructure.controller;

import com.knowy.server.application.domain.UserExercise;
import com.knowy.server.application.domain.UserLesson;
import com.knowy.server.application.exception.KnowyDataAccessException;
import com.knowy.server.application.exception.KnowyExerciseNotFoundException;
import com.knowy.server.application.service.UserExerciseService;
import com.knowy.server.application.service.UserLessonService;
import com.knowy.server.application.service.exception.KnowyLessonNotFoundException;
import com.knowy.server.application.service.exception.KnowyUserLessonNotFoundException;
import com.knowy.server.application.service.exception.KnowyUserNotFoundException;
import com.knowy.server.application.service.model.ExerciseDifficult;
import com.knowy.server.infrastructure.security.UserSecurityDetails;
import com.knowy.server.infrastructure.controller.dto.ExerciseDto;
import com.knowy.server.infrastructure.controller.dto.ExerciseOptionDto;
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

	private final UserExerciseService userExerciseService;
	private final UserLessonService userLessonService;

	/**
	 * The constructor
	 *
	 * @param userExerciseService the publicUserExerciseService
	 */
	public ExerciseController(UserExerciseService userExerciseService, UserLessonService userLessonService) {
		this.userExerciseService = userExerciseService;
		this.userLessonService = userLessonService;
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
			UserExercise userExercise = userExerciseService
				.getNextExerciseByLessonId(userDetails.getUser().id(), lessonId);

			UserLesson userLesson = userLessonService.findById(userDetails.getUser().id(), lessonId)
				.orElseThrow(() -> new KnowyUserLessonNotFoundException(
					"UserLesson not found for user ID: " + userDetails.getUser().id() + " and lesson ID: " + lessonId
				));

			model.addAttribute(EXERCISE_MODEL_ATTRIBUTE, ExerciseDto.fromDomain(userExercise, userLesson.lesson()));
			model.addAttribute("mode", "ANSWERING");
			model.addAttribute("formReviewUrl", "/course/exercise/review");
			return EXERCISE_HTML_URL;
		} catch (KnowyExerciseNotFoundException | KnowyDataAccessException | KnowyUserLessonNotFoundException e) {
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
	 * @throws KnowyExerciseNotFoundException if the exercise is not found
	 * @throws KnowyUserNotFoundException     if the user is not found
	 */
	@PostMapping("/course/exercise/review")
	public String exerciseLessonReview(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("answerId") int answerId,
		Model model
	) throws KnowyExerciseNotFoundException, KnowyUserNotFoundException, KnowyDataAccessException, KnowyUserLessonNotFoundException {

		UserExercise userExercise = userExerciseService.getByIdOrCreate(userDetails.getUser().id(),
			exerciseId);

		UserLesson userLesson = userLessonService
			.findById(userDetails.getUser().id(), userExercise.exercise().lessonId())
			.orElseThrow(() -> new KnowyUserLessonNotFoundException(
				"UserLesson not found for user ID: " + userDetails.getUser().id() + " and lesson ID: "
					+ userExercise.exercise().lessonId()
			));

		ExerciseDto exerciseDto = ExerciseDto.fromDomain(userExercise, userLesson.lesson(), answerId);

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
	 * @throws KnowyExerciseNotFoundException   if the exercise is not found
	 * @throws KnowyUserNotFoundException       if the user is not found
	 * @throws KnowyUserLessonNotFoundException if the publicUserLesson is not found
	 */
	@PostMapping("/course/exercise/evaluate")
	public String exerciseLessonEvaluate(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("evaluation") ExerciseDifficult evaluation
	) throws KnowyExerciseNotFoundException, KnowyUserNotFoundException, KnowyUserLessonNotFoundException, KnowyDataAccessException, KnowyLessonNotFoundException {
		UserExercise userExercise = userExerciseService
			.getByIdOrCreate(userDetails.getUser().id(), exerciseId);

		UserLesson userLesson = userLessonService
			.findById(userDetails.getUser().id(), userExercise.exercise().lessonId())
			.orElseThrow(() -> new KnowyUserLessonNotFoundException(
				"UserLesson not found for user ID: " + userDetails.getUser().id() + " and lesson ID: "
					+ userExercise.exercise().lessonId()
			));

		userExerciseService.processUserAnswer(evaluation, userExercise);

		int lessonId = userExercise.exercise().lessonId();
		int courseId = userLesson.lesson().course().id();

		double average = userExerciseService.getAverageRateByLessonId(lessonId);
		if (average >= 80) {
			userLessonService.updateLessonStatusToCompleted(userDetails.getUser(), userLesson.lesson());
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
			UserExercise userExercise = userExerciseService
				.getNextExerciseByUserId(userDetails.getUser().id());

			UserLesson userLesson = userLessonService
				.findById(userDetails.getUser().id(), userExercise.exercise().lessonId())
				.orElseThrow(() -> new KnowyUserLessonNotFoundException(
					"UserLesson not found for user ID: " + userDetails.getUser().id() + " and lesson ID: "
						+ userExercise.exercise().lessonId()
				));

			model.addAttribute(EXERCISE_MODEL_ATTRIBUTE, ExerciseDto.fromDomain(userExercise, userLesson.lesson()));
			model.addAttribute("mode", "ANSWERING");
			model.addAttribute("formReviewUrl", "/exercise/review");
			return EXERCISE_HTML_URL;
		} catch (KnowyExerciseNotFoundException | KnowyUserLessonNotFoundException | KnowyDataAccessException e) {
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
	 * @throws KnowyExerciseNotFoundException if the exercise cannot be found
	 * @throws KnowyUserNotFoundException     if the user cannot be found
	 */
	@PostMapping("/exercise/review")
	public String exerciseReview(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("answerId") int answerId,
		Model model
	) throws KnowyExerciseNotFoundException, KnowyUserNotFoundException, KnowyDataAccessException, KnowyUserLessonNotFoundException {

		UserExercise userExercise = userExerciseService
			.getByIdOrCreate(userDetails.getUser().id(), exerciseId);

		UserLesson userLesson = userLessonService
			.findById(userDetails.getUser().id(), userExercise.exercise().lessonId())
			.orElseThrow(() -> new KnowyUserLessonNotFoundException(
				"UserLesson not found for user ID: " + userDetails.getUser().id() + " and lesson ID: "
					+ userExercise.exercise().lessonId()
			));

		ExerciseDto exerciseDto = ExerciseDto.fromDomain(
			userExercise,
			userLesson.lesson(),
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
	 * @throws KnowyExerciseNotFoundException if the exercise cannot be found
	 * @throws KnowyUserNotFoundException     if the user cannot be found
	 */
	@PostMapping("/exercise/evaluate")
	public String exerciseEvaluate(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("evaluation") ExerciseDifficult evaluation
	) throws KnowyExerciseNotFoundException, KnowyUserNotFoundException, KnowyDataAccessException {
		UserExercise userExercise = userExerciseService
			.getByIdOrCreate(userDetails.getUser().id(), exerciseId);

		userExerciseService.processUserAnswer(evaluation, userExercise);
		return "redirect:/exercise/review";
	}
}
