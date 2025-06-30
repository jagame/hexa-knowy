package com.knowy.server.controller;



import com.knowy.server.controller.dto.CourseDTO;
import com.knowy.server.controller.dto.LessonDTO;
import com.knowy.server.controller.dto.LinksLessonDto;
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

		LessonDTO lesson = new LessonDTO(1, "JavaScript moderno", LessonDTO.LessonStatus.BLOCKED);
		//This does not work and crashes the whole branch.
		//LessonDTO lesson = new LessonDTO("JavaScript moderno", "100 horas", "https://media.licdn.com/dms/image/v2/D4D12AQG_8eaFpxIX8g/article-cover_image-shrink_600_2000/article-cover_image-shrink_600_2000/0/1685624189443?e=2147483647&v=beta&t=sYff1Zh5dsatOSYyUiMR8zwn3VqkG3x55bwFbEo5D-g", "#", "Teoria de la leccion");
		model.addAttribute("lesson", lesson);

		CourseDTO course = new CourseDTO();
		course.setName("Java Básico");
		course.setPercentageCompleted(40);

		List<LessonDTO> lessons = List.of(
			new LessonDTO(1, "Introducción", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(2, "¿Qué es Java y cómo funciona?", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(3, "Tipos de datos y variables", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(5, "Bucles", LessonDTO.LessonStatus.COMPLETE) ,
			new LessonDTO(6, "Métodos (funciones)", LessonDTO.LessonStatus.COMPLETE),
			new LessonDTO(7, "Arreglos (arrays)", LessonDTO.LessonStatus.NEXT_LESSON),
			new LessonDTO(8, "Introducción a la Programación Orientada a Objetos", LessonDTO.LessonStatus.BLOCKED),
			new LessonDTO(9, "Introducción a la Programación Orientada a Objetos", LessonDTO.LessonStatus.BLOCKED),
			new LessonDTO(10, "Introducción a la Programación Orientada a Objetos", LessonDTO.LessonStatus.BLOCKED)
		);

		int lastLesson = -1;
		for(int i = 0; i < lessons.size(); i++) {
			if(lessons.get(i).getStatus() == LessonDTO.LessonStatus.COMPLETE) {
				lastLesson = i;
			}
		}
		course.setLessons(lessons);
		model.addAttribute("course", course);
		model.addAttribute("lastLesson", lastLesson);

		// Array of links from each lesson?
		List<LinksLessonDto> LinksLessonList = new ArrayList<>();

		//External links
		LinksLessonList.add(new LinksLessonDto(
			"Ecosia - Buscador ecológico",
			"https://www.ecosia.org",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		LinksLessonList.add(new LinksLessonDto(
			"Wikipedia - Enciclopedia libre",
			"https://es.wikipedia.org",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		LinksLessonList.add(new LinksLessonDto(
			"MDN Web Docs - JavaScript",
			"https://developer.mozilla.org/es/docs/Web/JavaScript",
			LinksLessonDto.LinkType.EXTERNAL, ""
		));

		//Downloadable documents
		LinksLessonList.add(new LinksLessonDto(
			"Guía de JavaScript ES6+",
			"/documents/javascript-es6-guide.pdf",
			LinksLessonDto.LinkType.DOCUMENT, "javascript-es6-guide.pdf"
		));

		LinksLessonList.add(new LinksLessonDto(
			"Ejercicios prácticos",
			"/documents/javascript-exercises.zip",
			LinksLessonDto.LinkType.DOCUMENT, "javascript-exercises.zip"
		));

		model.addAttribute("LinksLessonList", LinksLessonList);

		return "pages/lesson-explanation";

	}
	@PostMapping("/feedback/submit") //he usado este controller porque estaba relacionado, si hace falta lo meto en otra pantalla
	public String submitEval(@RequestParam("dificultad") String dificultad, RedirectAttributes redirectAttributes) {
		System.out.println("Dificultad seleccionada por el usuario: " + dificultad);
		valoracion = Integer.parseInt(dificultad); //aquí invocaríamos un metodo de cada pregunta para guardar el feedback y luego procesarlo. Debería hacer dto? yo creo que debería ser el dto de la tarjeta guardada.
		redirectAttributes.addFlashAttribute("mensajeFeedback", "Gracias por tu feedback: " + dificultad);
		return "redirect:/"; //este redirect irá a la próxima tarjeta de la lección.
	}
}
