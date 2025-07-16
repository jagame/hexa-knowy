package com.knowy.server.controller;

import com.knowy.server.controller.dto.ExerciseDto;
import com.knowy.server.entity.PublicUserExerciseEntity;
import com.knowy.server.service.UserFacadeService;
import com.knowy.server.service.model.UserSecurityDetails;
import com.knowy.server.util.exception.ExerciseNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
public class ExerciseController {

	private final UserFacadeService userFacadeService;

	public ExerciseController(UserFacadeService userFacadeService) {
		this.userFacadeService = userFacadeService;
	}

	@GetMapping("/lesson/{lessonId}/exercise")
	public String exercise(
		@PathVariable("lessonId") int lessonId,
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		Model model
	) {
		try {
			PublicUserExerciseEntity publicUserExercise = userFacadeService
				.getNextExercise(userDetails.getPublicUser().getId(), lessonId);

			model.addAttribute("exercise", ExerciseDto.fromPublicUserExerciseEntity(publicUserExercise));
			model.addAttribute("mode", "ANSWERING");
			return "pages/exercise";
		} catch (ExerciseNotFoundException e) {
			return "error/error";
		}
	}

	@PostMapping("/lesson/{lessonId}/exercise")
	public String exerciseResponse(
		@AuthenticationPrincipal UserSecurityDetails userDetails,
		@PathVariable("lessonId") int lessonId,
		@RequestParam("exerciseId") int exerciseId,
		@RequestParam("answerId") String answerId,
		Model model
	) throws ExerciseNotFoundException {
		PublicUserExerciseEntity publicUserExercise = userFacadeService
			.getPublicUserExerciseById(userDetails.getPublicUser().getId(), exerciseId);

		ExerciseDto exerciseDto = ExerciseDto
			.fromPublicUserExerciseEntity(publicUserExercise, Integer.parseInt(answerId));

		model.addAttribute("exercise", exerciseDto);
		model.addAttribute("mode", "REVIEWING");
		return "pages/exercise";
	}
}
