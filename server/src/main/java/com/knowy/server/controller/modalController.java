//package com.knowy.server.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//@Controller
//public class modalController {
//
//	private static int valoracion;
//
//
//	@PostMapping("/feedback/submit")
//	public String submitEval(@RequestParam("dificultad") String dificultad, RedirectAttributes redirectAttributes) {
//		System.out.println("Dificultad seleccionada por el usuario: " + dificultad);
//		valoracion = Integer.parseInt(dificultad); //aquí invocaríamos un metodo de srvc para luego almacenar. Hago DTO?
//		redirectAttributes.addFlashAttribute("mensajeFeedback", "Gracias por tu feedback: " + dificultad);
//		return "redirect:/";
//	}
//}