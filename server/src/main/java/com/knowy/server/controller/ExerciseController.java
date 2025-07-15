package com.knowy.server.controller;

import com.knowy.server.controller.dto.ExerciseDto;
import com.knowy.server.controller.dto.OptionsDto;
import com.knowy.server.controller.dto.QuestionDTO;
import com.knowy.server.controller.dto.QuizLayoutDTO;
import com.knowy.server.entity.ExerciseEntity;
import com.knowy.server.service.ExerciseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ExerciseController {


	private final ExerciseService exerciseService;

	public ExerciseController(ExerciseService exerciseService) {
		this.exerciseService = exerciseService;
	}

	@GetMapping("/exercise/{id}")
	public String exercises(@PathVariable("id") int id, Model model) {
		ExerciseEntity exerciseEntity = exerciseService.findById(id);

		List<OptionsDto> options = exerciseEntity.getOptions()
			.stream()
			.map(OptionsDto::fromEntity)
			.toList();

		ExerciseDto exerciseDTO = new ExerciseDto(
			1,
			exerciseEntity.getLesson().getId(),
			exerciseEntity.getId(),
			"NA1",
			3,
			4,
			exerciseEntity.getQuestion(),
			"NA1",
			options
		);

		model.addAttribute("exercise", exerciseDTO);
		return "pages/tests";
	}

	// FIXME - This is a temporary endpoint for testing purposes
	@GetMapping("/asdasd")
	public String viewComponents(@RequestParam(defaultValue = "3") int quizID, ModelMap model) {
//		List<OptionQuizDTO> options = exerciseService.getOptionsForQuiz(quizID);
		QuizLayoutDTO quizLayoutDTO = new QuizLayoutDTO(
			"Java Básico",
			2,
			9,
			8,
			75
		);
		model.addAttribute("quizLayout", quizLayoutDTO);
//		model.addAttribute("options", options);

		QuestionDTO questionDTO = new QuestionDTO("1", "Question", "images/knowylogo.png");
		model.addAttribute("questionNumber", questionDTO.getQuestionNumber());
		model.addAttribute("questionText", questionDTO.getQuestionText());
		model.addAttribute("imgPath", questionDTO.getImgPath());

		return "pages/tests";
	}
}
