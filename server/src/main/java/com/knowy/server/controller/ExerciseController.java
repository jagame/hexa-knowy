package com.knowy.server.controller;

import com.knowy.server.controller.dto.ExerciseDto;
import com.knowy.server.controller.dto.ExerciseOptionDto;
import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.service.PublicUserExerciseService;
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

	private final PublicUserExerciseService publicUserExerciseService;

	public ExerciseController(PublicUserExerciseService publicUserExerciseService) {
		this.publicUserExerciseService = publicUserExerciseService;
	}

	@GetMapping("/course/{lessonId}/exercise/review")
	public String exercise(
		@PathVariable("lessonId") int lessonId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		Model model
	) {
		try {
			PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
				.getNextExerciseByLessonId(userDetails.getPublicUser().getId(), lessonId);

			model.addAttribute("exercise", ExerciseDto.fromPublicUserExerciseEntity(publicUserExercise));
			model.addAttribute("mode", "ANSWERING");
			return "pages/exercise";
		} catch (ExerciseNotFoundException e) {
			return "error/error";
		}
	}

	@PostMapping("/course/exercise/review")
	public String exerciseResponse(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("answerId") int answerId,
		Model model
	) throws ExerciseNotFoundException {
		PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
			.getById(userDetails.getPublicUser().getId(), exerciseId);

		ExerciseDto exerciseDto = ExerciseDto
			.fromPublicUserExerciseEntity(publicUserExercise, answerId);

		if (!isCorrectAnswer(exerciseDto.options(), answerId)) {
			model.addAttribute("mode", "FAILING");
		} else {
			model.addAttribute("mode", "REVIEWING");
		}
		model.addAttribute("exercise", exerciseDto);
		return "pages/exercise";
	}

	private boolean isCorrectAnswer(List<ExerciseOptionDto> options, int answer) {
		return options.stream()
			.filter(option -> option.id() == answer)
			.anyMatch(option -> option.status() == ExerciseOptionDto.AnswerStatus.RESPONSE_SUCCESS);
	}

	@PostMapping("/course/exercise/evaluate")
	public String exerciseEvaluate(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("evaluation") ExerciseDifficult evaluation
	) throws ExerciseNotFoundException {
		PublicUserExerciseEntity publicUserExercise = publicUserExerciseService
			.getById(userDetails.getPublicUser().getId(), exerciseId);

		publicUserExerciseService.processUserAnswer(evaluation, publicUserExercise);

		int lessonId = publicUserExercise.getExerciseEntity().getLesson().getId();

		return "redirect:/course/" + lessonId + "/exercise/review";
	}
}
