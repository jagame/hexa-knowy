package com.knowy.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MissionsController {
	@GetMapping("/Test")
	public String MissionsCtrl(ModelMap interfaceScreen) { //estos atributos de controladore serían en la futura página de usuario
		interfaceScreen.addAttribute("leccionesCompletadas", 20);
		interfaceScreen.addAttribute("progresoPorcentaje2", 20);
		interfaceScreen.addAttribute("leccionesTotales", 20);
		interfaceScreen.addAttribute("linkRetoDiario", "/");
		interfaceScreen.addAttribute("rachaActual2", 10);
		return "Test";
	}
}
