package com.knowy.server.controller;

import com.knowy.server.controller.model.OptionsQuestionnaireDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Controller
public class QuestionnaireController {

	//User-Account
	@GetMapping("/test")
	public String viewComponents(ModelMap model) {
		model.addAttribute("prueba", "pruebaaaaaaaaaaa");

		List<OptionsQuestionnaireDTO> options = Arrays.asList(
			new OptionsQuestionnaireDTO("A.", "Esta respuesta es errónea.", false),
			new OptionsQuestionnaireDTO("B.","Esta respuesta muy buena gente pero falsa.", false),
			new OptionsQuestionnaireDTO("C.","Esta respuesta mola cantidubi.", true),
			new OptionsQuestionnaireDTO("D.", "Esta respuesta te traerá dolores de cabeza.", false)
		);


		model.addAttribute("options", options);

		return "pages/test";
	}
}
