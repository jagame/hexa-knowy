package com.knowy.server.controller;

import com.knowy.server.controller.dto.OptionQuizDTO;
import com.knowy.server.controller.dto.QuestionDTO;
import com.knowy.server.controller.dto.QuizLayoutDTO;
import com.knowy.server.service.OptionQuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class QuizController {


	private final OptionQuizService optionQuizService;


	public QuizController(OptionQuizService optionQuizService) {
		this.optionQuizService = optionQuizService;
	}


	//User-Account
	@GetMapping("/quiz")
	public String viewComponents(@RequestParam(defaultValue = "3") int quizID, ModelMap model) {
		List<OptionQuizDTO> options = optionQuizService.getOptionsForQuiz(quizID);
		QuizLayoutDTO quizLayoutDTO = new QuizLayoutDTO(
			"Java Básico",
			2,
			9,
			8,
			75
		);
		model.addAttribute("quizLayout", quizLayoutDTO);
		model.addAttribute("options", options);

		QuestionDTO questionDTO = new QuestionDTO("1", "Question", "images/knowylogo.png");
		model.addAttribute("questionNumber", questionDTO.getQuestionNumber());
		model.addAttribute("questionText", questionDTO.getQuestionText());
		model.addAttribute("imgPath", questionDTO.getImgPath());

		return "pages/tests";
	}
}
