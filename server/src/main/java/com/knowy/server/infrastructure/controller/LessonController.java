package com.knowy.server.infrastructure.controller;


import com.knowy.server.infrastructure.controller.dto.LessonDTO;
import com.knowy.server.infrastructure.controller.dto.LinksLessonDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LessonController {
	private static int valoracion;

	@GetMapping("/lesson")
	public String showLesson(Model model) {

		LessonDTO lesson = new LessonDTO(
			1,
			"JavaScript moderno",
			"https://picsum.photos/seed/picsum/1000/1000",
			2,
			LessonDTO.LessonStatus.BLOCKED
		);
		model.addAttribute("lesson", lesson);

		// Array of links from each lesson?
		List<LinksLessonDto> linksLessonList = new ArrayList<>();

		//External links
		linksLessonList.add(new LinksLessonDto(
			"Ecosia - Buscador ecológico",
			"https://www.ecosia.org",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		linksLessonList.add(new LinksLessonDto(
			"Wikipedia - Enciclopedia libre",
			"https://es.wikipedia.org",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		linksLessonList.add(new LinksLessonDto(
			"MDN Web Docs - JavaScript",
			"https://developer.mozilla.org/es/docs/Web/JavaScript",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		//Downloadable documents
		linksLessonList.add(new LinksLessonDto(
			"Guía de JavaScript ES6+",
			"/documents/javascript-es6-guide.pdf",
			LinksLessonDto.LinkType.DOCUMENT, "javascript-es6-guide.pdf"
		));

		linksLessonList.add(new LinksLessonDto(
			"Ejercicios prácticos",
			"/documents/javascript-exercises.zip",
			LinksLessonDto.LinkType.DOCUMENT, "javascript-exercises.zip"
		));


		model.addAttribute("LinksLessonList", linksLessonList);

		return "pages/lesson-explanation";
	}

	@PostMapping("/feedback/submit")
	//he usado este controller porque estaba relacionado, si hace falta lo meto en otra pantalla
	public String submitEval(@RequestParam("dificultad") String dificultad, RedirectAttributes redirectAttributes) {
		System.out.println("Dificultad seleccionada por el usuario: " + dificultad);
		valoracion = Integer.parseInt(dificultad); //aquí invocaríamos un metodo de cada pregunta para guardar el feedback y luego procesarlo. Debería hacer dto? yo creo que debería ser el dto de la tarjeta guardada.
		redirectAttributes.addFlashAttribute("mensajeFeedback", "Gracias por tu feedback: " + dificultad);
		return "redirect:/"; //este redirect irá a la próxima tarjeta de la lección.
	}
}