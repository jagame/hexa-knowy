package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuestionnaireController {

	//User-Account
	@GetMapping("/test")
	public String viewComponents(ModelMap interfaceScreen) {
		interfaceScreen.addAttribute("prueba", "pruebaaaaaaaaaaa");
		return "pages/test";
	}
}
