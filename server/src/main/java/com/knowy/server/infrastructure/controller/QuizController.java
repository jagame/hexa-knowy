package com.knowy.server.infrastructure.controller;

import com.knowy.server.infrastructure.controller.dto.OptionQuizDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class QuizController {

	//OptionsQuiz
	@GetMapping("/testOptionsQuiz")
	public String viewComponents(@RequestParam(defaultValue = "3") int quizID, ModelMap model) {
		//Instead of creating the repository and the service, I create the dummy data for later development....
		List<OptionQuizDTO> options = Arrays.asList(
			new OptionQuizDTO(1, 2, 3, "A.", "Esta respuesta es errónea.", false),
			new OptionQuizDTO(1, 2, 3, "B.", "Esta respuesta es muy buena gente pero falsa.", false),
			new OptionQuizDTO(1, 2, 3, "C.", "Esta respuesta mola cantidubi.", true),
			new OptionQuizDTO(1, 2, 3, "D.", "Esta respuesta te traerá dolores de cabeza.", false)
		);
		model.addAttribute("options", options);
		return "/pages/testOptionsQuiz";
	}
}
