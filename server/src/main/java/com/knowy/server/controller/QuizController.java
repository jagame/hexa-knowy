package com.knowy.server.controller;

import com.knowy.server.controller.model.OptionQuizDTO;
import com.knowy.server.service.OptionQuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class QuizController {

	private final OptionQuizService optionQuizService;

	public  QuizController(OptionQuizService optionQuizService) {
		this.optionQuizService = optionQuizService;
	}

	//User-Account
	@GetMapping("/testOptionsQuiz")
	public String viewComponents(@RequestParam(defaultValue = "3") int quizID, ModelMap model) {
		List<OptionQuizDTO> options = optionQuizService.getOptionsForQuiz(quizID);
		model.addAttribute("options", options);
		return "/pages/testOptionsQuiz";
	}
}
